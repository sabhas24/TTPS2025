import { Component } from '@angular/core';
import { MisMascotas } from '../mis-mascotas/mis-mascotas';
import { RouterModule } from '@angular/router';


@Component({
    selector: 'app-mascotas-perdidas-page',
    standalone: true,
    imports: [MisMascotas, RouterModule,],
    template: `
    <app-mis-mascotas
      [modo]="'perdidas'"
      [mostrarAcciones]="false"
      [titulo]="'Mascotas Perdidas'">
    </app-mis-mascotas>
  `
})
export class MascotasPerdidasPage { }
