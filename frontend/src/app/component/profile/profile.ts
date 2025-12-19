import { Component, type OnInit, ChangeDetectorRef } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router, RouterModule } from "@angular/router"
import { FormBuilder, type FormGroup, ReactiveFormsModule, Validators } from "@angular/forms"
import { UsuarioService } from "../../services/usuario-service"
import { AuthService } from "../../services/auth-service"
import type { Usuario } from "../../interfaces/usuario"
import type { Mascota } from "../../interfaces/mascota"
import { EstadoMascota } from "../../interfaces/enums"
import { MascotaService } from "../../services/mascota-service"
import { HomeHeader } from "../home/home-header/home-header"
import { HomeFooter } from "../home/home-footer/home-footer"

@Component({
  selector: "app-profile",
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule, HomeFooter, HomeHeader],
  templateUrl: "./profile.html",
  styleUrl: "./profile.css",
})
export class Profile implements OnInit {
  usuario: Usuario | null = null
  mascotas: Mascota[] = []
  activeTab: "mascotas" | "ranking" = "mascotas"
  isLoading = false

  // Paginación
  currentPage = 1
  pageSize = 6
  totalPages = 0
  totalElements = 0

  profileForm: FormGroup
  isEditMode = false
  successMessage = ""
  errorMessage = ""
  selectedImage: string | null = null
  imageFile: File | null = null


  rankingPosition = 5
  rankingPercentile = 5
  rankingPoints = 2450
  rankingMaxPoints = 3000
  nextLevel = "Leyenda de Mascotas"

  userId: number | null = null
  private hasLoadedUserData = false

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private mascotaService: MascotaService,
    private router: Router,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
  ) {
    this.profileForm = this.fb.group({
      nombre: ["", [Validators.required, Validators.minLength(2)]],
      apellido: ["", [Validators.required, Validators.minLength(2)]],
      email: ["", [Validators.required, Validators.email, Validators.minLength(5)]],
      telefono: ["", [Validators.required, Validators.pattern(/^[0-9]{10,15}$/)]],
      barrio: ["", [Validators.required, Validators.minLength(2)]],
      ciudad: ["", [Validators.required, Validators.minLength(2)]],
      foto: [""],
    })
  }

  ngOnInit(): void {
    // Nos suscribimos al usuario del AuthService para asegurarnos
    // de que el ID esté disponible antes de cargar datos.
    this.authService.usuario$.subscribe((usuarioAuth) => {
      if (!usuarioAuth || this.hasLoadedUserData) {
        return
      }

      this.userId = usuarioAuth.id
      this.hasLoadedUserData = true
      this.loadUserProfile()
      this.loadUserPets()
    })
  }

  loadUserProfile(): void {
    const userId = this.userId
    this.isLoading = true

    if (userId == null) {
      this.isLoading = false
      this.errorMessage = "No se pudo obtener el ID de usuario."
      return
    }

    this.usuarioService.getPerfil(userId).subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario
        this.profileForm.patchValue({
          nombre: usuario.nombre,
          apellido: usuario.apellido,
          email: usuario.email,
          telefono: usuario.telefono,
          barrio: usuario.barrio,
          ciudad: usuario.ciudad,
        })
        if (usuario.foto) {
          this.selectedImage = usuario.foto
        }
        this.isLoading = false
        this.profileForm.disable()
        this.cdr.detectChanges()
      },
      error: (error: any) => {
        console.error("Error loading profile:", error)
        this.isLoading = false
      },
    })
  }

  loadUserPets(): void {
    if (this.userId == null) {
      this.mascotas = []
      return
    }

    this.mascotaService.getMascotasPorUsuarioPaginado(this.userId, this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.mascotas = response.content.map(mascota => ({
          ...mascota,
          estado: EstadoMascota[mascota.estado as keyof typeof EstadoMascota] ?? mascota.estado,
          coordenada: {
            ...mascota.coordenada,
            barrio: mascota.coordenada.barrio ?? ""
          }
        }))
        this.totalPages = response.totalPages
        this.totalElements = response.totalElements ?? 0
        this.cdr.detectChanges()
      },
      error: (error: any) => {
        console.error("Error cargando mascotas del usuario:", error)
        this.mascotas = []
        this.totalPages = 0
        this.totalElements = 0
      },
    })
  }

  // Métodos de paginación
  goToPage(page: number): void {
    if (page < 1 || page > this.totalPages) return
    this.currentPage = page
    this.loadUserPets()
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++
      this.loadUserPets()
    }
  }

  prevPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--
      this.loadUserPets()
    }
  }

  get pages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1)
  }

  setActiveTab(tab: "mascotas" | "ranking"): void {
    this.activeTab = tab
  }

  getEstadoLabel(estado: EstadoMascota): string {
    switch (estado) {
      case EstadoMascota.PERDIDO_PROPIO:
      case EstadoMascota.PERDIDO_AJENO:
        return "Perdido"
      case EstadoMascota.EN_POSESION:
        return "Encontrado"
      case EstadoMascota.EN_ADOPCION:
        return "En adopción"
      case EstadoMascota.RECUPERADO:
        return "Reunido"
      case EstadoMascota.ADOPTADO:
        return "Adoptado"
      default:
        return ""
    }
  }

  getEstadoClass(estado: EstadoMascota): string {
    switch (estado) {
      case EstadoMascota.PERDIDO_PROPIO:
      case EstadoMascota.PERDIDO_AJENO:
        return "badge-perdido"
      case EstadoMascota.EN_POSESION:
      case EstadoMascota.EN_ADOPCION:
        return "badge-encontrado"
      case EstadoMascota.RECUPERADO:
      case EstadoMascota.ADOPTADO:
        return "badge-reunido"
      default:
        return ""
    }
  }

  getFechaTexto(mascota: Mascota): string {
    // TODO: Get real dates from backend
    const date = new Date()
    const dateStr = date.toLocaleDateString("es-AR")

    switch (mascota.estado) {
      case EstadoMascota.PERDIDO_PROPIO:
      case EstadoMascota.PERDIDO_AJENO:
        return `Perdido desde: ${dateStr}`
      case EstadoMascota.EN_POSESION:
      case EstadoMascota.EN_ADOPCION:
        return `Encontrado el: ${dateStr}`
      case EstadoMascota.RECUPERADO:
      case EstadoMascota.ADOPTADO:
        return `Reunido el: ${dateStr}`
      default:
        return ""
    }
  }

  get rankingProgress(): number {
    return (this.rankingPoints / this.rankingMaxPoints) * 100
  }

  get pointsToNextLevel(): number {
    return this.rankingMaxPoints - this.rankingPoints
  }

  editProfile(): void {
    this.router.navigate(["/perfil/editar"])
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode
    if (this.isEditMode) {
      this.profileForm.enable()
      this.profileForm.get("email")?.disable()
      this.successMessage = ""
      this.errorMessage = ""
    } else {
      this.profileForm.disable()
      this.loadUserProfile()
    }
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement
    if (input.files && input.files[0]) {
      this.imageFile = input.files[0]
      const reader = new FileReader()
      reader.onload = (e: ProgressEvent<FileReader>) => {
        this.selectedImage = e.target?.result as string
      }
      reader.readAsDataURL(this.imageFile)
    }
  }

  triggerFileInput(): void {
    const fileInput = document.getElementById("fileInput") as HTMLInputElement
    fileInput?.click()
  }

  removeImage(): void {
    this.selectedImage = null
    this.imageFile = null
    const fileInput = document.getElementById("fileInput") as HTMLInputElement
    if (fileInput) {
      fileInput.value = ""
    }
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.markFormGroupTouched(this.profileForm)
      return
    }

    this.isLoading = true
    this.successMessage = ""
    this.errorMessage = ""

    const userId = 1

    const updateData: Partial<Usuario> = {
      ...this.profileForm.value,
      foto: this.selectedImage || undefined,
    }

    this.usuarioService.editarPerfil(userId, updateData).subscribe({
      next: (usuario: Usuario) => {
        this.successMessage = "Perfil actualizado exitosamente"
        this.isEditMode = false
        this.profileForm.disable()
        this.isLoading = false

        setTimeout(() => {
          this.successMessage = ""
        }, 3000)
      },
      error: (error: any) => {
        console.error("Error updating profile:", error)
        this.errorMessage = "No se pudo actualizar el perfil. Por favor, intenta de nuevo."
        this.isLoading = false
      },
    })
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach((key) => {
      const control = formGroup.get(key)
      control?.markAsTouched()
    })
  }

  getErrorMessage(fieldName: string): string {
    const control = this.profileForm.get(fieldName)
    if (control?.hasError("required")) {
      return "Este campo es obligatorio"
    }
    if (control?.hasError("minlength")) {
      return `Mínimo ${control.errors?.["minlength"].requiredLength} caracteres`
    }
    if (control?.hasError("email")) {
      return "Ingresa un correo electrónico válido"
    }
    if (control?.hasError("pattern")) {
      return "Ingresa un número de teléfono válido (10-15 dígitos)"
    }
    return ""
  }

  hasError(fieldName: string): boolean {
    const control = this.profileForm.get(fieldName)
    return !!(control?.invalid && control?.touched)
  }
}
