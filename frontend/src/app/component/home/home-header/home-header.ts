import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth-service';

interface NavLink {
  label: string;
  path: string;
  requiereLogin?: boolean; // ahora opcional
}

@Component({
  selector: 'app-home-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-header.html',
  styleUrls: ['./home-header.css'],
})
export class HomeHeader {
  authService = inject(AuthService);
  router = inject(Router);

  navLinks: NavLink[] = [
    { label: 'Inicio', path: '/' },
    // { label: 'Mascotas Perdidas', path: '/mascotas-perdidas' }, // Ruta pendiente de implementar
    { label: 'Registrar Mascota Perdida', path: '/mascotas/crear', requiereLogin: true },
    { label: 'Mis Mascotas', path: '/mascotas/mis-mascotas', requiereLogin: true }
  ];

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
