import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth-service';
import { HomeHeader } from '../../home/home-header/home-header';
import { GeorefService } from '../../../services/georef.service';
import { MapaComponent } from '../../mapa/mapa.component';

@Component({
  selector: 'app-registrar-mascota',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HomeHeader,
    MapaComponent
  ],
  templateUrl: './registrar-mascota.html',
  styleUrls: ['./registrar-mascota.css'],
})
export class RegistrarMascota {

  nombre = '';
  tamanio = '';
  color = '';
  descripcionExtra = '';
  fechaPerdida = '';

  tipoPerdida: 'PROPIO' | 'AJENO' = 'PROPIO';

  lat: number = -34.9205; // valor por defecto
  lon: number = -57.9536;

  fotos: File[] = [];

  usuarioNombre = '';
  usuarioId: number | null = null;

  constructor(
    public authService: AuthService,
    private georefService: GeorefService
  ) {
    const usuario = this.authService.getUsuario();

    if (usuario) {
      this.usuarioNombre = `${usuario.nombre} ${usuario.apellido}`;
      this.usuarioId = usuario.id;

      // Revisamos si hay coordenadas guardadas en localStorage
      const stored = localStorage.getItem('ubicacionUsuario');
      if (stored) {
        const coord = JSON.parse(stored);
        this.lat = coord.lat;
        this.lon = coord.lon;
        console.log('Coordenadas cargadas desde localStorage:', this.lat, this.lon);
      } else {
        // Obtenemos coordenadas desde Georef según barrio o ciudad
        const lugar = usuario.barrio || usuario.ciudad;
        if (lugar) {
          this.georefService.obtenerCentroideLocalidad(lugar)
            .subscribe({
              next: (coord) => {
                this.lat = coord.lat;
                this.lon = coord.lon;
                console.log('Coordenadas iniciales desde Georef:', this.lat, this.lon);

                // Guardamos en localStorage
                localStorage.setItem('ubicacionUsuario', JSON.stringify({ lat: this.lat, lon: this.lon }));
              },
              error: () => {
                console.warn('No se pudo obtener la ubicación inicial');
              }
            });
        }
      }
    }
  }

  onFotoSeleccionada(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.fotos = Array.from(input.files);
    }
  }

  onMapaSeleccionado(event: { lat: number; lon: number }) {
    this.lat = event.lat;
    this.lon = event.lon;

    // Guardamos la ubicación seleccionada en localStorage
    localStorage.setItem('ubicacionUsuario', JSON.stringify({ lat: this.lat, lon: this.lon }));
  }

  submit() {
    if (!this.usuarioId) {
      alert('Debe iniciar sesión para publicar una mascota');
      return;
    }

    const mascotaDTO = {
      nombre: this.nombre,
      tamanio: this.tamanio,
      color: this.color,
      descripcionExtra: this.descripcionExtra,
      fechaPerdida: this.fechaPerdida,
      tipoPerdida: this.tipoPerdida,
      coordenada: {
        latitud: this.lat,
        longitud: this.lon
      },
      fotos: this.fotos,
      publicadorId: this.usuarioId
    };

    console.log('Mascota a publicar:', mascotaDTO);
  }
}
