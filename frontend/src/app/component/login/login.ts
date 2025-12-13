import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth'
import { UsuarioLogin } from '../../interfaces/usuario';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  email = '';
  password = '';
  error = '';

  constructor(private authService: AuthService) { }

  onSubmit() {
    const body: UsuarioLogin = { email: this.email, contraseña: this.password };

    this.authService.login(body).subscribe({
      next: () => {

      },
      error: err => {
        this.error = 'Credenciales inválidas';
        console.error(err);
      }
    });
  }

}
