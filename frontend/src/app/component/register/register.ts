import { Component } from "@angular/core"
import { ReactiveFormsModule, FormBuilder, type FormGroup, Validators } from "@angular/forms"
import { CommonModule } from "@angular/common"
import { Router, RouterLink } from "@angular/router"
import { AuthService } from "../../services/auth-service"
import type { UsuarioCreate } from "../../interfaces/usuario"
import { usuarioTipo } from "../../interfaces/enums"
import { HomeFooter } from "../home/home-footer/home-footer"


@Component({
  selector: "app-register",
  imports: [ReactiveFormsModule, CommonModule, HomeFooter, RouterLink],
  templateUrl: "./register.html",
  styleUrl: "./register.css",
})
export class Register {
  registerForm: FormGroup
  error: string | null = null
  imagePreview: string | null = null
  selectedFile: File | null = null
  isSubmitting = false
  imageError: string | null = null
  isCompressing = false
  maxSizeKB = 500

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
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
    this.imageError = null

    if (input.files && input.files[0]) {
      const file = input.files[0]

      if (!file.type.startsWith('image/')) {
        this.imageError = 'Por favor seleccioná un archivo de imagen válido'
        input.value = ''
        return
      }

      const maxSizeMB = 5
      if (file.size > maxSizeMB * 1024 * 1024) {
        this.imageError = `La imagen es muy grande. Máximo ${maxSizeMB}MB`
        input.value = ''
        return
      }

      this.selectedFile = file
      this.compressAndPreview(file)
    }
  }

  compressAndPreview(file: File): void {
    this.isCompressing = true
    const reader = new FileReader()

    reader.onload = (e) => {
      const img = new Image()
      img.onload = () => {
        const canvas = document.createElement('canvas')
        let width = img.width
        let height = img.height

        const maxDimension = 800
        if (width > maxDimension || height > maxDimension) {
          if (width > height) {
            height = (height * maxDimension) / width
            width = maxDimension
          } else {
            width = (width * maxDimension) / height
            height = maxDimension
          }
        }

        canvas.width = width
        canvas.height = height

        const ctx = canvas.getContext('2d')
        ctx?.drawImage(img, 0, 0, width, height)

        let quality = 0.8
        let compressedBase64 = canvas.toDataURL('image/jpeg', quality)

        while (compressedBase64.length > this.maxSizeKB * 1024 * 1.37 && quality > 0.1) {
          quality -= 0.1
          compressedBase64 = canvas.toDataURL('image/jpeg', quality)
        }

        this.imagePreview = compressedBase64
        this.isCompressing = false

        const finalSizeKB = Math.round(compressedBase64.length / 1024)
        if (finalSizeKB > this.maxSizeKB * 1.5) {
          this.imageError = `La imagen comprimida (${finalSizeKB}KB) sigue siendo grande. Intentá con otra más pequeña.`
        }
      }
      img.src = e.target?.result as string
    }

    reader.readAsDataURL(file)
  }

  removeImage(): void {
    this.selectedFile = null
    this.imagePreview = null
    this.imageError = null
    const fileInput = document.getElementById('file-upload') as HTMLInputElement
    if (fileInput) {
      fileInput.value = ''
    }
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
        this.isSubmitting = false
        this.router.navigate(['/login'])
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
