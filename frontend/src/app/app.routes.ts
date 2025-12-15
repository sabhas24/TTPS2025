import { Routes } from '@angular/router';
import { Login } from './component/login/login';
import { Register } from './component/register/register';
import { HomeComponent } from './component/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },   // ahora HomeComponent es la ruta raíz
  { path: 'login', component: Login },
  { path: 'register', component: Register }
];
