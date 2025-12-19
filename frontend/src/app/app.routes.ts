import type { Routes } from "@angular/router";
import { Login } from "./component/login/login";
import { Register } from "./component/register/register";
import { Home } from "./component/home/home";
import { Profile } from "./component/profile/profile";
import { ProfileEdit } from "./component/profile-edit/profile-edit";
import { ForgotPassword } from "./component/forgot-password/forgot-password";
import { ResetPassword } from "./component/reset-password/reset-password";

import { AuthGuard } from "./guard/auth.guard";

export const routes: Routes = [
  { path: "", component: Home },
  { path: "login", component: Login },
  { path: "register", component: Register },
  { path: "perfil", component: Profile, canActivate: [AuthGuard] },
  { path: "perfil/editar", component: ProfileEdit, canActivate: [AuthGuard] },
  { path: "forgot-password", component: ForgotPassword },
  { path: "auth/reset-password", component: ResetPassword },
  {
    path: 'mascotas/crear',
    loadComponent: () =>
      import('./component/mascota/registrar-mascota/registrar-mascota')
        .then(m => m.RegistrarMascota),
    canActivate: [AuthGuard]
  },
  {
    path: 'mascotas/mis-mascotas',
    loadComponent: () =>
      import('./component/mascota/mis-mascotas-page/mis-mascotas-page')
        .then(m => m.MisMascotasPage),
    canActivate: [AuthGuard]
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
        .then(m => m.EditarMascotaPerdida),
    canActivate: [AuthGuard]
  },


  // AVISTAMIENTOS
  {
    path: "mascotas/detalle/:id/avistamientos/nuevo",
    loadComponent: () => import("./component/avistamientos/form-avistamientos/avistamiento-form.compontent").then(m => m.AvistamientoFormComponent),
    canActivate: [AuthGuard]
  },
  {
    path: "mascotas/detalle/:id/avistamientos/:avistamientoId",
    loadComponent: () => import("./component/avistamientos/detalle-avistamientos/avistamiento-detail.component").then(m => m.AvistamientoDetailComponent)
  },
  {
    path: "mascotas/detalle/:id/avistamientos/:avistamientoId/editar",
    loadComponent: () => import("./component/avistamientos/form-avistamientos/avistamiento-form.compontent").then(m => m.AvistamientoFormComponent),
    canActivate: [AuthGuard]
  },

  // 2. RUTA DE DETALLE MASCOTA (Con listado integrado)
  {
    path: "mascotas/detalle/:id",
    loadComponent: () => import("./component/mascota/detalle-mascota/detalle-mascota").then((m) => m.DetalleMascota),
    children: [
      {
        path: "", // Al entrar al detalle, se carga el listado automáticamente
        loadComponent: () => import("./component/avistamientos/lista-avistamientos/avistamiento-list.component").then(m => m.AvistamientoListComponent)
      }
    ]
  },
];
