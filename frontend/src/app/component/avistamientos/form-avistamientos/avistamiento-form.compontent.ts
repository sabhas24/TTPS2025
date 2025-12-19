import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';import {
  AvistamientoCreateRequest,
  AvistamientoUpdateRequest,
  AvistamientoResponse
} from '../../../interfaces/avistamiento';
import { AvistamientoService } from '../../../services/avistamiento-service';
import { CommonModule, DatePipe } from '@angular/common';
import { AuthService } from '../../../services/auth-service';
import { MascotaService } from '../../../services/mascota-service';
import { ValidatorFn, AbstractControl } from '@angular/forms';
import { MapaComponent } from '../../mapa/mapa.component';
import { GeorefService } from '../../../services/georef.service';

@Component({
  selector: 'app-avistamiento-form',
  templateUrl: './avistamiento-form.compontent.html',
  styleUrls: ["./form-avistamientos.css"],
  standalone: true,imports: [
    CommonModule,
    ReactiveFormsModule,
    MapaComponent
  ]
})
export class AvistamientoFormComponent implements OnInit {
  private cdr = inject(ChangeDetectorRef);
  form!: FormGroup;
  editando = false;
  avistamientoId?: number;
  mascotaId!: number;
  usuarioId!: number;
  fotosBase64: string[] = [];
  nombreMascota: string = '';
  nombreUsuario: string = '';
  lat: number = -34.9205;
  lon: number = -57.9536;
  lugarNombre: string = 'Buscando ubicación...';

  constructor(
    private fb: FormBuilder,
    private service: AvistamientoService,
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private georefService: GeorefService
  ) {}


  private fechaNoFuturaValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const fechaSeleccionada = new Date(control.value);
      const ahora = new Date();
      return fechaSeleccionada > ahora ? { fechaFutura: true } : null;
    };
  }


ngOnInit(): void {
    const idMascotaParam = this.route.snapshot.paramMap.get('id');
    const idAvistamientoParam = this.route.snapshot.paramMap.get('avistamientoId');

    const idUsuario = this.authService.getUsuarioId();
    if (idUsuario) {
      this.usuarioId = idUsuario;
      this.nombreUsuario = this.authService.getNombreCompleto();
    } else {
      this.router.navigate(['/login']);
      return;
    }

    this.initForm();

    if (idMascotaParam) {
      this.mascotaId = Number(idMascotaParam);
      this.mascotaService.getMascotaById(this.mascotaId).subscribe({
        next: (m) => {
          this.nombreMascota = m.nombre;
          // =========================
          // NUEVO (no rompe nada)
          // =========================
          if (m.coordenada?.barrio) {
            this.georefService.obtenerCentroideLocalidad(m.coordenada?.barrio).subscribe({
              next: (coord) => {
                this.lat = coord.lat;
                this.lon = coord.lon;

                this.form.patchValue({
                  latitud: coord.lat,
                  longitud: coord.lon,
                  barrio: m.coordenada?.barrio
                });

                this.cdr.detectChanges();
              },
              error: () => {
                console.warn('No se pudo centrar el mapa con georef');
              }
            });
          }
          // =========================
          this.cdr.detectChanges();
        }
      });
    }

    if (idAvistamientoParam) {
      this.avistamientoId = Number(idAvistamientoParam);
      this.editando = true;
      this.cargarDatos();
    }
  }

  onFotoSeleccionada(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files) return;

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

  eliminarFoto(index: number) {
    this.fotosBase64.splice(index, 1);
  }

  private initForm() {
    this.form = this.fb.group({
      comentario: ['', [Validators.maxLength(500)]],
      enPosesion: [false],
      fecha: [new Date().toISOString().substring(0, 16), [Validators.required, this.fechaNoFuturaValidator()]],
      latitud: [null, Validators.required],
      longitud: [null, Validators.required],
      barrio: ['', Validators.required],
    });
  }

  private cargarDatos() {
    this.service.obtenerDetalle(this.avistamientoId!).subscribe({
      next: (data) => {
        this.form.patchValue({
          comentario: data.comentario,
          enPosesion: data.enPosesion,
          fecha: data.fecha ? data.fecha.substring(0, 16) : '',
          latitud: data.coordenada?.latitud,
          longitud: data.coordenada?.longitud,
          barrio: data.coordenada?.barrio,
        });
        if (data.fotos) this.fotosBase64 = data.fotos;
      }
    });
  }
  guardar() {
    console.log('Estado del formulario:', this.form.value);
    console.log('¿Es válido?:', this.form.valid);
if (this.form.invalid) {
    // Esto mostrará EXACTAMENTE qué campo está fallando
    Object.keys(this.form.controls).forEach(key => {
      const controlErrors = this.form.get(key)?.errors;
      if (controlErrors != null) {
        console.log('Error en campo: ' + key, controlErrors);
      }
    });
    this.form.markAllAsTouched();
    window.alert('Por favor, completa todos los campos obligatorios.');
    return;
  }

    const val = this.form.value;
    const coordenada = { latitud: val.latitud, longitud: val.longitud, barrio: val.barrio };

    if (this.editando && this.avistamientoId) {
      const updateReq: AvistamientoUpdateRequest = { ...val, coordenada, fotos: this.fotosBase64 };
      this.service.updateAvistamiento(this.avistamientoId, updateReq).subscribe({
        next: () => {
          window.alert('¡Avistamiento actualizado con éxito!');
          this.navegarAlListado();
        },
        error: () => window.alert('Error al actualizar el avistamiento.')
      });
    } else {
      const createReq: AvistamientoCreateRequest = {
        mascotaId: this.mascotaId,
        usuarioId: this.usuarioId,
        ...val,
        fecha: new Date(val.fecha).toISOString(),
        coordenada,
        foto: this.fotosBase64
      };

      this.service.createAvistamiento(createReq).subscribe({
        next: () => {
          window.alert('¡Gracias! El reporte ha sido enviado.');
          this.navegarAlListado();
        },
        error: () => window.alert('Hubo un problema al enviar el reporte.')
      });
    }
  }

  private navegarAlListado() {
    this.router.navigate(['/mascotas/detalle', this.mascotaId]);
  }

  volver() {
    this.router.navigate(['/mascotas/detalle', this.mascotaId]);
  }

  onMapaSeleccionado(event: { lat: number; lon: number }) {
    this.form.patchValue({
      latitud: event.lat,
      longitud: event.lon
    });

    // Forzamos la validación de estos campos
    this.form.get('latitud')?.markAsDirty();
    this.form.get('longitud')?.markAsDirty();

    this.lugarNombre = 'Buscando ubicación...';
    this.cdr.detectChanges();

    this.georefService.obtenerUbicacion(event.lat, event.lon).subscribe({
      next: (ubicacion) => {
        this.lugarNombre = ubicacion.municipio_nombre || ubicacion.departamento_nombre || 'Ubicación identificada';
        this.form.patchValue({ barrio: this.lugarNombre });
        this.form.get('barrio')?.markAsDirty(); // Importante para que el formulario sepa que cambió
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error en georef inversa', err);
        window.alert('Error en georef inversa' + err);
        this.lugarNombre = 'Ubicación seleccionada';this.form.patchValue({ barrio: this.lugarNombre });
        this.form.get('barrio')?.markAsDirty();
        this.cdr.detectChanges();
      }
    });
  }
}
