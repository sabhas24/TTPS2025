import { Component, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MascotaService, Mascota } from '../../../services/mascota-service';
import { AuthService } from '../../../services/auth-service';
import { HomeHeader } from '../../home/home-header/home-header';

@Component({
  selector: 'app-editar-mascota-perdida',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HomeHeader,
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

  // ✅ Expresiones regulares
  private regexNombreColor = /^[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+$/;
  private regexDescripcion = /^[a-zA-ZÁÉÍÓÚáéíóúñÑ0-9 ,.]+$/;

  // ✅ Errores por campo
  validaciones = {
    nombre: '',
    tamanio: '',
    color: '',
    descripcion: ''
  };

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private mascotaService: MascotaService,
    private authService: AuthService,
    private ngZone: NgZone,
    private cdRef: ChangeDetectorRef
  ) {}

  volver() {
    this.router.navigate(['/mascotas/mis-mascotas']);
  }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!id) {
      this.error = 'ID de mascota inválido';
      this.cargando = false;
      return;
    }

    this.mascotaService.getMascotaById(id).subscribe({
      next: (res) => {
        console.log("✅ Mascota recibida:", res);
        this.mascota = res;

        if (!this.mascota.coordenada) {
          this.mascota.coordenada = { latitud: 0, longitud: 0, barrio: '' };
        }

        this.cargando = false;
        this.cdRef.detectChanges();
      },
      error: (err) => {
        console.error("❌ Error al cargar mascota:", err);
        this.error = 'No se pudo cargar la mascota';
        this.cargando = false;
      }
    });
  }

  onFotosChange(value: string) {
    this.mascota.fotos = value.split(',').map(f => f.trim());
  }

  actualizarMascota() {

    // ✅ Validar nombre
    if (!this.regexNombreColor.test(this.mascota.nombre)) {
      this.validaciones.nombre = 'El nombre solo puede contener letras y espacios';
    } else {
      this.validaciones.nombre = '';
    }

    // ✅ Validar tamaño
    if (this.mascota.tamanio <= 0) {
      this.validaciones.tamanio = 'El tamaño debe ser un número positivo';
    } else {
      this.validaciones.tamanio = '';
    }

    // ✅ Validar color
    if (!this.regexNombreColor.test(this.mascota.color)) {
      this.validaciones.color = 'El color solo puede contener letras y espacios';
    } else {
      this.validaciones.color = '';
    }

    // ✅ Validar descripción
    if (this.mascota.descripcionExtra && !this.regexDescripcion.test(this.mascota.descripcionExtra)) {
      this.validaciones.descripcion = 'La descripción solo puede contener letras, números, espacios, comas y puntos';
    } else {
      this.validaciones.descripcion = '';
    }

    // ✅ Si hay errores, no enviar
    if (
      this.validaciones.nombre ||
      this.validaciones.tamanio ||
      this.validaciones.color ||
      this.validaciones.descripcion
    ) {
      this.error = 'Hay errores en el formulario';
      return;
    }

    this.error = '';

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

        this.router.navigate(['/mascotas/mis-mascotas']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar la mascota';
      }
    });
  }

}
