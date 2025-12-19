import type { Routes } from "@angular/router";
import { Login } from "./component/login/login";
import { Register } from "./component/register/register";
import { Home } from "./component/home/home";
import { Profile } from "./component/profile/profile";
import { ProfileEdit } from "./component/profile-edit/profile-edit";

export const routes: Routes = [
  { path: "", component: Home },
  { path: "login", component: Login },
  { path: "register", component: Register },
  { path: "perfil", component: Profile },
  { path: "perfil/editar", component: ProfileEdit },
  {
    path: 'mascotas/crear',
    loadComponent: () =>
      import('./component/mascota/registrar-mascota/registrar-mascota')
        .then(m => m.RegistrarMascota)
  },
  {
    path: 'mascotas/mis-mascotas',
    loadComponent: () =>
      import('./component/mascota/mis-mascotas-page/mis-mascotas-page')
        .then(m => m.MisMascotasPage)
  },
  {
    path: 'mascotas/perdidas',
    loadComponent: () =>
      import('./component/mascota/mascotas-perdidas-page/mascotas-perdidas-page')
        .then(m => m.MascotasPerdidasPage)
  },

  {
    path: 'mascotas/editar/:id',
    loadComponent: () =>
      import('./component/mascota/editar-mascota-perdida/editar-mascota-perdida')
        .then(m => m.EditarMascotaPerdida)
  },

  {
    path: "mascotas/detalle/:id",
    loadComponent: () => import("./component/mascota/detalle-mascota/detalle-mascota").then((m) => m.DetalleMascota),
  },

];
