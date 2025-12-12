import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { authInterceptor } from './interceptor/auth-interceptor';
import { routes } from './app.routes';
import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { LoginRequest } from '../interfaces/auth';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes)
  ]
};

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  email = '';
  password = '';
  error = '';

  constructor(private authService: AuthService) { }

  onSubmit() {
    const body: LoginRequest = { email: this.email, password: this.password };

    this.authService.login(body).subscribe({
      next: () => {
        // aquí ya se guardó el token
        // redirigir, etc.
      },
      error: err => {
        this.error = 'Credenciales inválidas';
        console.error(err);
      }
    });
  }
}
