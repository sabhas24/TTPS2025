import type { Routes } from "@angular/router";
import { Login } from "./component/login/login";
import { Register } from "./component/register/register";
import { Home } from "./component/home/home";
import { RegistrarMascota } from './component/mascota/registrar-mascota/registrar-mascota';

export const routes: Routes = [
  { path: "", component: Home },
  { path: "login", component: Login },
  { path: "register", component: Register },
  {
    path: 'mascotas/crear',
    loadComponent: () =>
      import('./component/mascota/registrar-mascota/registrar-mascota')
        .then(m => m.RegistrarMascota)
  },
  {
    path: 'mascotas/mis-mascotas',
    loadComponent: () =>
      import('./component/mascota/mis-mascotas/mis-mascotas')
        .then(m => m.MisMascotas)
  },
  {
    path: 'mascotas/editar/:id',   // 👈 agregamos la ruta con parámetro id
    loadComponent: () =>
      import('./component/mascota/editar-mascota-perdida/editar-mascota-perdida')
        .then(m => m.EditarMascotaPerdida)
  }
];
