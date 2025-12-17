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

    this.cargando = true;

    this.mascotaService.getMascotasPorUsuarioPaginado(usuarioId, this.paginaActual, this.mascotasPorPagina)
      .subscribe({
        next: (res) => {
          this.mascotasPaginadas = res.content;
          this.totalPaginas = res.totalPages;
          this.cargando = false;
        },
        error: (err) => {
          console.error(err);
          this.error = 'Error al cargar mascotas';
          this.cargando = false;
        }
      });
  }

  siguientePagina() {
    if (this.paginaActual < this.totalPaginas) {
      this.paginaActual++;
      this.listarMascotas();
    }
  }

  paginaAnterior() {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.listarMascotas();
    }
  }

  editarMascota(id: number) {
    this.router.navigate([`/mascotas/editar/${id}`]);
  }

  eliminarMascota(id: number) {
    if (!confirm('¿Desea eliminar esta mascota?')) return;

    this.mascotaService.deleteMascota(id).subscribe({
      next: () => {
        // Ajustar página si eliminamos el último elemento de la última página
        if (this.mascotasPaginadas.length === 1 && this.paginaActual > 1) {
          this.paginaActual--;
        }
        // Volvemos a cargar la página actual desde backend
        this.listarMascotas();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al eliminar mascota';
      }
    });
  }

}
