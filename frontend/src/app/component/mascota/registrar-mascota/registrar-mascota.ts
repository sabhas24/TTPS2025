import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth-service';
import { HomeHeader } from '../../home/home-header/home-header'; // 👈 IMPORT CLAVE

@Component({
  selector: 'app-registrar-mascota',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HomeHeader // 👈 IMPORTADO ACÁ
  ],
  templateUrl: './registrar-mascota.html',
  styleUrl: './registrar-mascota.css',
})
export class RegistrarMascota {

  nombre = '';
  tamanio = '';
  color = '';
  descripcionExtra = '';
  fechaPerdida = '';

  tipoPerdida: 'PROPIO' | 'AJENO' = 'PROPIO';

  latitud: number | null = null;
  longitud: number | null = null;

  fotos: File[] = [];

  constructor(public authService: AuthService) {}

  onFotoSeleccionada(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.fotos = Array.from(input.files);
    }
  }

  onMapaSeleccionado(lat: number, lng: number) {
    this.latitud = lat;
    this.longitud = lng;
  }

  submit() {
    const mascotaDTO = {
      nombre: this.nombre,
      tamanio: this.tamanio,
      color: this.color,
      descripcionExtra: this.descripcionExtra,
      fechaPerdida: this.fechaPerdida,
      tipoPerdida: this.tipoPerdida,
      coordenada: {
        latitud: this.latitud,
        longitud: this.longitud
      },
      fotos: this.fotos,
      publicadorId: 'DESDE_JWT' // 🔒 luego se obtiene del token
    };

    console.log('Mascota a publicar:', mascotaDTO);
    // 👉 después conectamos POST /mascotas
  }
}
