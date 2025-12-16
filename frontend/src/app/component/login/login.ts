import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';


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

  constructor(private authService: AuthService,private router: Router
  ) {}

  onSubmit() {
    const body = {
      email: this.email,
      password: this.password   // 🔥 CLAVE
    };

    this.authService.login(body).subscribe({
      next: () => {
        // ✅ token YA se guarda en AuthService
        this.router.navigate(['/']); // 🔥 HOME
      },
      error: () => {
        this.error = 'Credenciales inválidas';
      }
    });
  }
}
