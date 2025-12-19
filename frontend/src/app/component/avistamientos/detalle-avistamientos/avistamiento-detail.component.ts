import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Avistamiento, AvistamientoResponse } from '../../../interfaces/avistamiento';
import { AvistamientoService } from '../../../services/avistamiento-service';
import { CommonModule } from '@angular/common';
import { UsuarioService } from 'app/services/usuario-service';
import { MascotaService } from 'app/services/mascota-service';
import { Mascota } from 'app/interfaces/mascota';
import { Usuario } from 'app/interfaces/usuario';

@Component({
  selector: 'app-avistamiento-detail',
  templateUrl: './avistamiento-detail.component.html',
  standalone: true,
  imports: [
    CommonModule,
  ]
})
export class AvistamientoDetailComponent implements OnInit {
  avistamiento?: AvistamientoResponse;
  cargando = true;
  mascotaId!: number;
  selectedImageIndex = 0;
  reporter?: string;
  mascota?: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: AvistamientoService,
    private usuarioService: UsuarioService,
    private mascotaService: MascotaService
  ) {}

    ngOnInit(): void {
        const idMascota = this.route.parent?.snapshot.paramMap.get('id');
        const idAvistamiento = this.route.snapshot.paramMap.get('avistamientoId');

        if (idMascota && idAvistamiento) {
            this.mascotaId = Number(idMascota);
            this.obtenerDetalle(Number(idAvistamiento));
        }
    }

  private obtenerDetalle(id: number) {
    this.cargando = true;
    this.service.obtenerDetalle(id).subscribe({
      next: (data) => {
        this.cargarDatosRelacionados(data);
        this.cargando = false;
      },
      error: () => (this.cargando = false),
    });
  }

  private cargarDatosRelacionados(avistamiento: AvistamientoResponse) {
    // 1. Cargar nombre del reportero
    if (avistamiento.usuarioId) {
      this.usuarioService.getPerfil(avistamiento.usuarioId).subscribe({
        next: (user) => this.reporter =  user ? `${user.nombre} ${user.apellido}` : '',
        error: (err) => console.error('Error cargando reportero', err)
      });
    }

    // 2. Cargar nombre de la mascota
    if (this.mascotaId) {
      this.mascotaService.getMascotaById(this.mascotaId).subscribe({
        next: (pet) => this.mascota = pet.nombre,
        error: (err) => console.error('Error cargando mascota', err)
      });
    }
  }

    selectImage(index: number): void {
        this.selectedImageIndex = index;
    }

    editar() {
        if (this.avistamiento?.id) {
        this.router.navigate(['editar'], { relativeTo: this.route });
        }
    }


    volver() {
        this.router.navigate(['/mascotas/detalle', this.mascotaId, 'avistamientos']);
    }
}
