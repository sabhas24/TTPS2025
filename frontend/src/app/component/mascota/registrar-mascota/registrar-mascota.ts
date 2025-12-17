import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../services/auth-service';
import { MascotaService, MascotaCreateDTO } from '../../../services/mascota-service';
import { GeorefService } from '../../../services/georef.service';

import { HomeHeader } from '../../home/home-header/home-header';
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

  // ===== Datos del formulario =====
  nombre: string = '';
  tamanio: number | null = null;
  color: string = '';
  descripcionExtra: string = '';

  // ===== Ubicación =====
  lat: number = -34.9205;
  lon: number = -57.9536;

  // ===== Fotos =====
  fotosBase64: string[] = [];

  // ===== Usuario =====
  usuarioNombre: string = '';
  usuarioId: number | null = null;

  constructor(
    public authService: AuthService,
    private georefService: GeorefService,
    private mascotaService: MascotaService,
    private router: Router
  ) {
    const usuario = this.authService.getUsuario();

    if (usuario) {
      this.usuarioNombre = `${usuario.nombre} ${usuario.apellido}`;
      this.usuarioId = usuario.id;

      const stored = localStorage.getItem('ubicacionUsuario');
      if (stored) {
        const coord = JSON.parse(stored);
        this.lat = coord.lat;
        this.lon = coord.lon;
      } else {
        const lugar = usuario.barrio || usuario.ciudad;
        if (lugar) {
          this.georefService.obtenerCentroideLocalidad(lugar).subscribe({
            next: (coord) => {
              this.lat = coord.lat;
              this.lon = coord.lon;
              localStorage.setItem(
                'ubicacionUsuario',
                JSON.stringify({ lat: this.lat, lon: this.lon })
              );
            },
            error: () => console.warn('No se pudo obtener la ubicación inicial')
          });
        }
      }
    }
  }

  // ===== Conversión de imágenes a Base64 con reducción =====
  onFotoSeleccionada(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files) return;

    this.fotosBase64 = [];

    Array.from(input.files).forEach(file => {
      const reader = new FileReader();
      reader.onload = (e) => {
        const img = new Image();
        img.onload = () => {
          const canvas = document.createElement('canvas');
          const maxWidth = 800; // ancho máximo
          const maxHeight = 800; // alto máximo
          let width = img.width;
          let height = img.height;

          // Mantener proporción
          if (width > height) {
            if (width > maxWidth) {
              height = (height * maxWidth) / width;
              width = maxWidth;
            }
          } else {
            if (height > maxHeight) {
              width = (width * maxHeight) / height;
              height = maxHeight;
            }
          }

          canvas.width = width;
          canvas.height = height;

          const ctx = canvas.getContext('2d');
          if (ctx) {
            ctx.drawImage(img, 0, 0, width, height);
            // Convertir a Base64 (calidad 70%)
            const compressed = canvas.toDataURL('image/jpeg', 0.7);
            this.fotosBase64.push(compressed);
          }
        };
        img.src = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    });
  }

  // ===== Selección desde el mapa =====
  onMapaSeleccionado(event: { lat: number; lon: number }) {
    this.lat = event.lat;
    this.lon = event.lon;
    localStorage.setItem('ubicacionUsuario', JSON.stringify({ lat: this.lat, lon: this.lon }));
  }

  // ===== Envío al backend con validación de tamaño total =====
  submit() {
    if (!this.usuarioId || this.tamanio === null || !this.nombre.trim()) {
      alert('Faltan datos obligatorios');
      return;
    }

    const barrioUsuario = this.authService.getUsuario()?.barrio || 'Sin especificar';

    const mascotaDTO: MascotaCreateDTO = {
      nombre: this.nombre,
      tamanio: this.tamanio!,
      color: this.color,
      descripcionExtra: this.descripcionExtra,
      fotos: this.fotosBase64,
      coordenada: {
        latitud: this.lat,
        longitud: this.lon,
        barrio: barrioUsuario
      },
      publicadorId: this.usuarioId
    };

    // Validar tamaño total aproximado del payload
    const payloadStr = JSON.stringify(mascotaDTO);
    const payloadSizeKB = new Blob([payloadStr]).size / 1024;
    const maxSizeKB = 2000; // 2 MB máximo

    if (payloadSizeKB > maxSizeKB) {
      alert(`El tamaño total de las imágenes es demasiado grande (${payloadSizeKB.toFixed(0)} KB). Reduzca las imágenes.`);
      return;
    }

    console.log('=== Mascota a enviar al backend ===', JSON.stringify(mascotaDTO, null, 2));

    this.mascotaService.createMascota(mascotaDTO).subscribe({
      next: (res) => {
        console.log('=== Respuesta del backend ===', res);
        alert('Mascota publicada correctamente');
        this.resetForm();
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('=== ERROR del backend ===', err);
        alert('Error al publicar la mascota, revisá la consola');
      }
    });
  }

  // ===== Reset del formulario =====
  private resetForm() {
    this.nombre = '';
    this.tamanio = null;
    this.color = '';
    this.descripcionExtra = '';
    this.fotosBase64 = [];
  }
}
