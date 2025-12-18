import { Component, type OnInit } from "@angular/core"
import { ReactiveFormsModule, FormBuilder, type FormGroup, Validators } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { Router, RouterLink } from "@angular/router"
import { UsuarioService } from "../../services/usuario-service"
import { AuthService } from "../../services/auth-service"
import type { Usuario } from "../../interfaces/usuario"

@Component({
  selector: "app-profile-edit",
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: "./profile-edit.html",
  styleUrl: "./profile-edit.css",
})
export class ProfileEdit implements OnInit {
  editForm: FormGroup
  error: string | null = null
  imagePreview: string | null = null
  selectedFile: File | null = null
  isSubmitting = false
  usuario: Usuario | null = null

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router,
  ) {
    this.editForm = this.fb.group({
      nombre: ["", [Validators.required, Validators.minLength(2)]],
      apellido: ["", [Validators.required, Validators.minLength(2)]],
      telefono: ["", [Validators.required, Validators.pattern(/^\d{10,15}$/)]],
      barrio: ["", [Validators.required]],
      ciudad: ["", [Validators.required]],
      cbu: ["", [Validators.pattern(/^\d{22}$/)]],
    })
  }

  ngOnInit(): void {
    this.loadUserProfile()
  }

  loadUserProfile(): void {
    const userId = this.authService.getUsuarioId()

    if (userId == null) {
      this.error = "Debes iniciar sesión para editar tu perfil"
      this.router.navigate(["/login"])
      return
    }

    this.usuarioService.getPerfil(userId).subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario
        this.editForm.patchValue({
          nombre: usuario.nombre,
          apellido: usuario.apellido,
          telefono: usuario.telefono,
          barrio: usuario.barrio,
          ciudad: usuario.ciudad,
        })
        if (usuario.foto) {
          this.imagePreview = usuario.foto
        }
      },
      error: (error) => {
        console.error("Error loading profile:", error)
        this.error = "No se pudo cargar el perfil"
      },
    })
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0]

      const reader = new FileReader()
      reader.onload = (e) => {
        this.imagePreview = e.target?.result as string
      }
      reader.readAsDataURL(this.selectedFile)
    }
  }

  removeImage(): void {
    this.selectedFile = null
    this.imagePreview = null
  }

  getControl(name: string) {
    return this.editForm.get(name)
  }

  hasError(fieldName: string, errorType: string): boolean {
    const field = this.editForm.get(fieldName)
    return !!(field?.hasError(errorType) && (field?.dirty || field?.touched))
  }

  onSubmit(): void {
    if (this.editForm.invalid) {
      this.editForm.markAllAsTouched()
      return
    }

    this.isSubmitting = true
    this.error = null

    const userId = this.authService.getUsuarioId()

    if (userId == null) {
      this.isSubmitting = false
      this.error = "Debes iniciar sesión para actualizar tu perfil"
      this.router.navigate(["/login"])
      return
    }
    const updateData: Partial<Usuario> = {
      ...this.editForm.value,
      foto: this.imagePreview || undefined,
    }

    this.usuarioService.editarPerfil(userId, updateData).subscribe({
      next: () => {
        this.router.navigate(["/profile"])
      },
      error: (err) => {
        this.isSubmitting = false
        this.error = "Error al actualizar el perfil, intenta más tarde"
      },
    })
  }

  cancel(): void {
    this.router.navigate(["/profile"])
  }
}
