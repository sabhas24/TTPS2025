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

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private mascotaService: MascotaService,
    private authService: AuthService,
    private ngZone: NgZone,
    private cdRef: ChangeDetectorRef  // ✅ agregado
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
        console.log("✅ Mascota recibida:", res);
        this.mascota = res;

        if (!this.mascota.coordenada) {
          this.mascota.coordenada = { latitud: 0, longitud: 0, barrio: '' };
        }

        this.cargando = false;
        this.cdRef.detectChanges();  // ✅ fuerza el renderizado
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
