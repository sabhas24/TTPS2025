import { Component } from '@angular/core';
import { MisMascotas } from '../mis-mascotas/mis-mascotas';

@Component({
    selector: 'app-mis-mascotas-page',
    standalone: true,
    imports: [MisMascotas],
    template: `
    <app-mis-mascotas 
      [modo]="'usuario'"
      [mostrarAcciones]="true"
      [titulo]="'Mis Mascotas'">
    </app-mis-mascotas>
  `
})
export class MisMascotasPage { }
