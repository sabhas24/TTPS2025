import { Routes } from '@angular/router';
import { AvistamientoListComponent } from './lista-avistamientos/avistamiento-list.component';
import { AvistamientoDetailComponent } from './detalle-avistamientos/avistamiento-detail.component';
import { AvistamientoFormComponent } from './form-avistamientos/avistamiento-form.compontent';

const routes: Routes = [
  { path: '', component: AvistamientoListComponent },
  { path: 'nuevo', component: AvistamientoFormComponent },
  { path: ':avistamientoId', component: AvistamientoDetailComponent },
  { path: ':avistamientoId/editar', component: AvistamientoFormComponent }
];

export default routes;
