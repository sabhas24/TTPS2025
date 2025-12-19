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

  nombre: string = '';
  tamanio: number | null = null;
  color: string = '';
  descripcionExtra: string = '';

  lat: number = -34.9205;
  lon: number = -57.9536;

  fotosBase64: string[] = [];

  usuarioNombre: string = '';
  usuarioId: number | null = null;

  // ✅ Expresiones regulares
  private regexNombreColor = /^[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+$/;
  private regexDescripcion = /^[a-zA-ZÁÉÍÓÚáéíóúñÑ0-9 ,.\/]+$/;

  // ✅ Errores por campo
  errores = {
    nombre: '',
    tamanio: '',
    color: '',
    descripcion: ''
  };

  constructor(
    public authService: AuthService,
    private georefService: GeorefService,
    private mascotaService: MascotaService,
    private router: Router
  ) {
    this.inicializarUbicacion();
  }

  // ===== Inicializar ubicación desde localStorage o calculando del usuario =====
  private inicializarUbicacion() {
    const usuario = this.authService.getUsuario();
    if (!usuario?.ciudad) return;

    this.usuarioNombre = `${usuario.nombre} ${usuario.apellido}`;
    this.usuarioId = usuario.id;

    const storedUbicacion = localStorage.getItem('ubicacionUsuario');
    const storedCiudad = localStorage.getItem('ciudadUsuario');

    if (storedUbicacion && storedCiudad === usuario.ciudad) {
      const coord = JSON.parse(storedUbicacion);
      this.lat = coord.lat;
      this.lon = coord.lon;
    } else {
      this.calcularUbicacionUsuario();
    }
  }

  private calcularUbicacionUsuario() {
    const usuario = this.authService.getUsuario();
    if (!usuario?.ciudad) return;

    const ciudad = usuario.ciudad.trim();

    this.georefService.obtenerCentroideLocalidad(ciudad).subscribe({
      next: (coord) => {
        this.lat = coord.lat;
        this.lon = coord.lon;

        localStorage.setItem(
          'ubicacionUsuario',
          JSON.stringify({ lat: this.lat, lon: this.lon })
        );

        localStorage.setItem('ciudadUsuario', ciudad);
      },
      error: () => console.warn('No se pudo obtener la ubicación inicial')
    });
  }

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
          const maxWidth = 800;
          const maxHeight = 800;
          let width = img.width;
          let height = img.height;

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
            const compressed = canvas.toDataURL('image/jpeg', 0.7);
            this.fotosBase64.push(compressed);
          }
        };
        img.src = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    });
  }

  onMapaSeleccionado(event: { lat: number; lon: number }) {
    this.lat = event.lat;
    this.lon = event.lon;
    localStorage.setItem('ubicacionUsuario', JSON.stringify({ lat: this.lat, lon: this.lon }));
  }

  // ===== Validaciones en tiempo real =====
  validarNombre() {
    if (!this.nombre.trim() || !this.regexNombreColor.test(this.nombre)) {
      this.errores.nombre = 'El nombre solo puede contener letras y espacios.';
    } else {
      this.errores.nombre = '';
    }
  }

  validarTamanio() {
    if (this.tamanio === null || this.tamanio <= 0) {
      this.errores.tamanio = 'El tamaño debe ser un número positivo.';
    } else if (this.tamanio > 150) {
      this.errores.tamanio = 'El tamaño no puede superar los 150 cm.';
    } else {
      this.errores.tamanio = '';
    }
  }

  validarColor() {
    if (this.color.trim() && !this.regexNombreColor.test(this.color)) {
      this.errores.color = 'El color solo puede contener letras y espacios.';
    } else {
      this.errores.color = '';
    }
  }

  validarDescripcion() {
    if (this.descripcionExtra.trim() && !this.regexDescripcion.test(this.descripcionExtra)) {
      this.errores.descripcion = 'La descripción solo puede contener letras, números, espacios, comas, puntos y "/".';
    } else {
      this.errores.descripcion = '';
    }
  }

  submit() {
    if (!this.usuarioId || this.tamanio === null || !this.nombre.trim()) {
      window.alert('⚠️ Faltan datos obligatorios para publicar la mascota.');
      return;
    }

    // ✅ Validaciones
    this.validarNombre();
    this.validarTamanio();
    this.validarColor();
    this.validarDescripcion();

    if (
      this.errores.nombre ||
      this.errores.tamanio ||
      this.errores.color ||
      this.errores.descripcion
    ) {
      window.alert('⚠️ Hay errores en el formulario. Revisá los campos.');
      return;
    }

    // no pasamos barrio sino ciudad
    const barrioUsuario =  this.authService.getUsuario()?.ciudad || 'Sin especificar';

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

    const payloadStr = JSON.stringify(mascotaDTO);
    const payloadSizeKB = new Blob([payloadStr]).size / 1024;
    const maxSizeKB = 2000;

    if (payloadSizeKB > maxSizeKB) {
      window.alert(`⚠️ El tamaño total de las imágenes es demasiado grande (${payloadSizeKB.toFixed(0)} KB). Reduzca las imágenes.`);
      return;
    }

    console.log('=== Mascota a enviar al backend ===', JSON.stringify(mascotaDTO, null, 2));

    this.mascotaService.createMascota(mascotaDTO).subscribe({
      next: (res) => {
        console.log('=== Respuesta del backend ===', res);
        window.alert('✅ Mascota publicada correctamente');
        this.resetForm();

        // Recalcular ubicación del usuario para el siguiente formulario
        this.calcularUbicacionUsuario();

        // Redirigimos al home
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('=== ERROR del backend ===', err);
        window.alert('❌ Error al publicar la mascota. Revisa la consola para más detalles.');
      }
    });
  }

  private resetForm() {
    this.nombre = '';
    this.tamanio = null;
    this.color = '';
    this.descripcionExtra = '';
    this.fotosBase64 = [];
  }
}
