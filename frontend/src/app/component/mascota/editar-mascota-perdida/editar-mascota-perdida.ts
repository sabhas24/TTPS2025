import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MascotaService, Mascota, CoordenadaDTO } from '../../../services/mascota-service';
import { AuthService } from '../../../services/auth-service';
import { HomeHeader } from '../../home/home-header/home-header';

@Component({
  selector: 'app-editar-mascota-perdida',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HomeHeader
  ],
  templateUrl: './editar-mascota-perdida.html',
  styleUrls: ['./editar-mascota-perdida.css']
})
export class EditarMascotaPerdida {

  mascota: Mascota = {
    id: 0,
    nombre: '',
    tamanio: 0,
    color: '',
    descripcionExtra: '',
    fotos: [],
    coordenada: { latitud: 0, longitud: 0, barrio: '' },
    publicadorId: 0,
    estado: '',
    nombrePublicador: ''
  };

  cargando: boolean = true;
  error: string = '';
  exito: string = '';

  constructor(
    private route: ActivatedRoute,
    public router: Router,  // 👈 router público para usar en HTML
    private mascotaService: MascotaService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error = 'ID de mascota inválido';
      this.cargando = false;
      return;
    }

    this.mascotaService.getMascotaById(id).subscribe({
      next: (res) => {
        this.mascota = res;
        // Si coordenada es null, inicializamos
        if (!this.mascota.coordenada) {
          this.mascota.coordenada = { latitud: 0, longitud: 0, barrio: '' };
        }
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudo cargar la mascota';
        this.cargando = false;
      }
    });
  }

  // Método para actualizar las fotos
  onFotosChange(value: string) {
    if (this.mascota) {
      this.mascota.fotos = value.split(',').map(f => f.trim());
    }
  }

  actualizarMascota() {
    if (!this.mascota) return;

    const dto = {
      nombre: this.mascota.nombre,
      color: this.mascota.color,
      tamanio: this.mascota.tamanio,
      descripcionExtra: this.mascota.descripcionExtra,
      fotos: this.mascota.fotos,
      coordenada: this.mascota.coordenada
    };

    this.mascotaService.updateMascota(this.mascota.id, dto).subscribe({
      next: () => {
        this.exito = 'Mascota actualizada correctamente';
        setTimeout(() => this.router.navigate(['/mis-mascotas']), 1500);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar la mascota';
      }
    });
  }
}
