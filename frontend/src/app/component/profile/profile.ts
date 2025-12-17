import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { type FormBuilder, type FormGroup, ReactiveFormsModule, Validators } from "@angular/forms"
import { RouterModule } from "@angular/router"
import type { UsuarioService } from "../../services/usuario-service"
import type { AuthService } from "../../services/auth-service"
import type { Usuario } from "../../interfaces/usuario"
import { HomeHeader } from "../home/home-header/home-header"
import { HomeFooter } from "../home/home-footer/home-footer"

@Component({
  selector: "app-profile",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, HomeHeader, HomeFooter],
  templateUrl: "./profile.html",
  styleUrl: "./profile.css",
})
export class Profile implements OnInit {
  profileForm: FormGroup
  isEditMode = false
  isLoading = false
  successMessage = ""
  errorMessage = ""
  selectedImage: string | null = null
  imageFile: File | null = null

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private authService: AuthService,
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
    this.loadUserProfile()
  }

  loadUserProfile(): void {
    // TODO: Obtener el ID del usuario del token o del AuthService
    const userId = 1 // Placeholder - reemplazar con el ID real del usuario logueado

    this.isLoading = true
    this.usuarioService.getPerfil(userId).subscribe({
      next: (usuario: Usuario) => {
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
      },
      error: (error) => {
        console.error("Error loading profile:", error)
        this.errorMessage = "No se pudo cargar el perfil. Por favor, intenta de nuevo."
        this.isLoading = false
      },
    })
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode
    if (this.isEditMode) {
      this.profileForm.enable()
      this.profileForm.get("email")?.disable() // Email no se puede editar
      this.successMessage = ""
      this.errorMessage = ""
    } else {
      this.profileForm.disable()
      this.loadUserProfile() // Recargar datos originales
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

    // TODO: Implementar lógica de subida de imagen si hay una nueva
    const userId = 1 // Placeholder - reemplazar con el ID real

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

        // Actualizar la vista con los nuevos datos
        setTimeout(() => {
          this.successMessage = ""
        }, 3000)
      },
      error: (error) => {
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
