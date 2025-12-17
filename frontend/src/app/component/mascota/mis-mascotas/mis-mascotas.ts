import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Mascota, MascotaService } from '../../../services/mascota-service';
import { AuthService } from '../../../services/auth-service';
import { HomeHeader } from '../../home/home-header/home-header';

@Component({
  selector: 'app-mis-mascotas',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HomeHeader
  ],
  templateUrl: './mis-mascotas.html',
  styleUrls: ['./mis-mascotas.css']
})
export class MisMascotas {
  mascotas: Mascota[] = [];
  mascotasPaginadas: Mascota[] = [];
  cargando: boolean = true;
  error: string = '';

  // Variables de paginación
  paginaActual: number = 1;
  mascotasPorPagina: number = 5;
  totalPaginas: number = 1;

  constructor(
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.listarMascotas();
  }

  listarMascotas() {
    const usuarioId = this.authService.getUsuarioId();
    if (!usuarioId) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    this.mascotaService.getMascotasPorUsuario(usuarioId).subscribe({
      next: (res) => {
        this.mascotas = res;
        this.totalPaginas = Math.ceil(this.mascotas.length / this.mascotasPorPagina);
        this.actualizarPaginacion();
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar mascotas';
        this.cargando = false;
      }
    });
  }

  actualizarPaginacion() {
    const start = (this.paginaActual - 1) * this.mascotasPorPagina;
    const end = start + this.mascotasPorPagina;
    this.mascotasPaginadas = this.mascotas.slice(start, end);
  }

  siguientePagina() {
    if (this.paginaActual < this.totalPaginas) {
      this.paginaActual++;
      this.actualizarPaginacion();
    }
  }

  paginaAnterior() {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.actualizarPaginacion();
    }
  }

  editarMascota(id: number) {
    this.router.navigate([`/mascotas/editar/${id}`]);
  }

  eliminarMascota(id: number) {
    if (!confirm('¿Desea eliminar esta mascota?')) return;

    this.mascotaService.deleteMascota(id).subscribe({
      next: () => {
        this.mascotas = this.mascotas.filter(m => m.id !== id);
        this.totalPaginas = Math.ceil(this.mascotas.length / this.mascotasPorPagina);
        if (this.paginaActual > this.totalPaginas) this.paginaActual = this.totalPaginas;
        this.actualizarPaginacion();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al eliminar mascota';
      }
    });
  }
}
