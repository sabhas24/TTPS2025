import { Component } from "@angular/core"
import { ReactiveFormsModule, FormBuilder, type FormGroup, Validators } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { RouterLink } from "@angular/router"
import { AuthService } from "../../services/auth-service"
import { HomeFooter } from "../home/home-footer/home-footer"
import type { UsuarioLogin } from "../../interfaces/usuario"

@Component({
  selector: "app-login",
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, HomeFooter],
  templateUrl: "./login.html",
  styleUrl: "./login.css",
})
export class Login {
  loginForm: FormGroup
  error: string | null = null
  isSubmitting = false
  showPassword = false

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
  ) {
    this.loginForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(8)]],
    })
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword
  }

  hasError(fieldName: string, errorType: string): boolean {
    const field = this.loginForm.get(fieldName)
    return !!(field?.hasError(errorType) && (field?.dirty || field?.touched))
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched()
      return
    }

    this.isSubmitting = true
    this.error = null

    const body: UsuarioLogin = {
      email: this.loginForm.value.email,
      contraseña: this.loginForm.value.password,
    }

    this.authService.login(body).subscribe({
      next: () => {
        console.log("Login exitoso")
        // Navigate to home or dashboard
      },
      error: (err) => {
        this.isSubmitting = false
        if (err.status === 401) {
          this.error = "Credenciales inválidas. Verificá tu email y contraseña."
        } else if (err.status === 404) {
          this.error = "Usuario no encontrado."
        } else {
          this.error = "Error del servidor. Intentá nuevamente más tarde."
        }
        console.error(err)
      },
    })
  }
}
