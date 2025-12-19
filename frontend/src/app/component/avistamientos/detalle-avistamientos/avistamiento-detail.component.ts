import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
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
  private cdr = inject(ChangeDetectorRef)

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: AvistamientoService,
    private usuarioService: UsuarioService,
    private mascotaService: MascotaService
  ) {}

    ngOnInit(): void {
        const idMascota = this.route.snapshot.paramMap.get('id');
        const idAvistamiento = this.route.snapshot.paramMap.get('avistamientoId');

        console.log("ids: " + idMascota + " " + idAvistamiento);
        if (idMascota && idAvistamiento) {
            console.log("Entró");
            this.mascotaId = Number(idMascota);
            this.obtenerDetalle(Number(idAvistamiento));
        }
    }

  private obtenerDetalle(id: number) {
    this.cargando = true;
    this.service.obtenerDetalle(id).subscribe({
      next: (data) => {
        console.log(data);
        this.avistamiento = data;
        this.cargarDatosRelacionados(data);
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.cargando = false;
        this.cdr.detectChanges();
        console.log("error")
      }
    });
  }

  private cargarDatosRelacionados(avistamiento: AvistamientoResponse) {
    // 1. Cargar nombre del reportero
    console.log("Cargando datos related")
    if (avistamiento.usuarioId) {
        console.log("Entró 1");
      this.usuarioService.getPerfil(avistamiento.usuarioId).subscribe({
        next: (user) => {
            this.reporter =  `${user.nombre} ${user.apellido}`
            this.cdr.detectChanges();
        },
        
            error: (err) => console.error('Error cargando reportero', err)
      });
    }

    // 2. Cargar nombre de la mascota
    if (this.mascotaId) {
        console.log("Entró 2");
      this.mascotaService.getMascotaById(this.mascotaId).subscribe(
        {next: (pet) => {
          this.mascota = pet.nombre;
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error cargando mascota', err)
      });
    }
  }

    selectImage(index: number): void {
        this.selectedImageIndex = index;
    }

    editar() {
        if (this.avistamiento?.id) {
        this.router.navigate(['editar'], { relativeTo: this.route });        }
    }


    volver() {
        this.router.navigate(['/mascotas/detalle', this.mascotaId]);
    }
}
