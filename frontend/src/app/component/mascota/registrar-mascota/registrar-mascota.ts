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

  usuarioNombre = '';
  usuarioId: number | null = null;


  constructor(public authService: AuthService) {
    const usuario = this.authService.getUsuario();

    if (usuario) {
      this.usuarioNombre = `${usuario.nombre} ${usuario.apellido}`;
      this.usuarioId = usuario.id;
    }

    console.log('Usuario JWT:', usuario);
  }

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
        latitud: this.latitud,
        longitud: this.longitud
      },
      fotos: this.fotos,
      publicadorId: this.usuarioId // 🔒 luego se obtiene del token
    };

    console.log('Mascota a publicar:', mascotaDTO);
    // 👉 después conectamos POST /mascotas
  }
}
