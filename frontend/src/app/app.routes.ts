import type { Routes } from "@angular/router"
import { Login } from "./component/login/login"
import { Register } from "./component/register/register"
import { Home } from "./component/home/home"
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
]
