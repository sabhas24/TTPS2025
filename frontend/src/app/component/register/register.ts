import { Component } from "@angular/core"
import { ReactiveFormsModule, FormBuilder, type FormGroup, Validators } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { AuthService } from "../../services/auth-service"
import type { UsuarioCreate } from "../../interfaces/usuario"
import { usuarioTipo } from "../../interfaces/enums"
import { RouterLink } from "@angular/router"

@Component({
  selector: "app-register",
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: "./register.html",
  styleUrl: "./register.css",
})
export class Register {
  registerForm: FormGroup
  error: string | null = null
  imagePreview: string | null = null
  selectedFile: File | null = null
  isSubmitting = false

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
  ) {
    this.registerForm = this.fb.group(
      {
        nombre: ["", [Validators.required, Validators.minLength(2)]],
        apellido: ["", [Validators.required, Validators.minLength(2)]],
        email: ["", [Validators.required, Validators.email]],
        password: ["", [Validators.required, Validators.minLength(8)]],
        passwordConfirm: ["", [Validators.required]],
        telefono: ["", [Validators.required, Validators.pattern(/^\d{10,15}$/)]],
        barrio: ["", [Validators.required]],
        ciudad: ["", [Validators.required]],
        cbu: ["", [Validators.pattern(/^\d{22}$/)]],
      },
      { validators: this.passwordMatchValidator },
    )
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get("password")
    const confirmPassword = form.get("passwordConfirm")
    return password && confirmPassword && password.value === confirmPassword.value ? null : { passwordMismatch: true }
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0]

      // Create preview
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
    return this.registerForm.get(name)
  }

  hasError(fieldName: string, errorType: string): boolean {
    const field = this.registerForm.get(fieldName)
    return !!(field?.hasError(errorType) && (field?.dirty || field?.touched))
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched()
      return
    }

    this.isSubmitting = true
    this.error = null

    const formValue = this.registerForm.value
    const body: UsuarioCreate = {
      nombre: formValue.nombre,
      apellido: formValue.apellido,
      email: formValue.email,
      contraseña: formValue.password,
      telefono: formValue.telefono,
      barrio: formValue.barrio,
      ciudad: formValue.ciudad,
      foto: this.imagePreview || "",
      tipo: usuarioTipo.USUARIO,
    }

    this.authService.register(body).subscribe({
      next: () => {
        // Success - could navigate to login or home
        console.log("Registro exitoso")
      },
      error: (err) => {
        this.isSubmitting = false
        if (err.status === 409) {
          this.error = "El email ya está registrado"
        } else if (err.status === 400) {
          this.error = err.error.message || "Datos inválidos"
        } else {
          this.error = "Error del servidor, intenta más tarde"
        }
      },
    })
  }
}
