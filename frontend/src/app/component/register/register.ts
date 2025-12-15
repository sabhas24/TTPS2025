import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { UsuarioCreate } from '../../interfaces/usuario';
import { usuarioTipo } from '../../interfaces/enums';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {
  nombre = '';
  apellido = '';
  email = '';
  password = '';
  telefono = '';
  barrio = '';
  ciudad = '';
  foto = '';
  passwordConfirm = '';
  tipo = usuarioTipo.USUARIO;
  error: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.password.length < 8) {
      this.error = 'La contraseña debe tener al menos 8 caracteres';
      return;
    }
    if (this.password !== this.passwordConfirm) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }

    const body: UsuarioCreate = {
      nombre: this.nombre,
      apellido: this.apellido,
      email: this.email,
      contraseña: this.password,
      telefono: this.telefono,
      barrio: this.barrio,
      ciudad: this.ciudad,
      foto: this.foto,
      tipo: this.tipo
    };

    // Registrarse y redirigir al home automáticamente
    this.authService.register(body).subscribe({
      next: () => {
        this.router.navigate(['/']); // redirige al home
      },
      error: err => {
        if (err.status === 409) {
          this.error = 'El email ya está registrado';
        } else if (err.status === 400) {
          this.error = err.error.message || 'Datos inválidos';
        } else {
          this.error = 'Error del servidor, intenta más tarde';
        }
      }
    });
  }
}
