import { Component } from '@angular/core';
import { MisMascotas } from '../mis-mascotas/mis-mascotas';

@Component({
    selector: 'app-mascotas-perdidas-page',
    standalone: true,
    imports: [MisMascotas],
    template: `
    <app-mis-mascotas 
      [modo]="'perdidas'"
      [mostrarAcciones]="false"
      [titulo]="'Mascotas Perdidas'">
    </app-mis-mascotas>
  `
})
export class MascotasPerdidasPage { }
