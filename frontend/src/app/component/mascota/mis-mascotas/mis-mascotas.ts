import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Mascota, MascotaService } from '../../../services/mascota-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-mis-mascotas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mis-mascotas.html',
  styleUrls: ['./mis-mascotas.css']
})
export class MisMascotas {
  mascotas: Mascota[] = [];
  cargando: boolean = true;
  error: string = '';

  constructor(
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.listarMascotas();
  }

  listarMascotas() {
    const usuario = this.authService.getUsuario();
    if (!usuario) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    // Usamos getMascotas() y filtramos por publicadorId
    this.mascotaService.getMascotas().subscribe({
      next: (res: Mascota[]) => {
        this.mascotas = res.filter(m => m.publicadorId === usuario.id);
        this.cargando = false;
      },
      error: (err: any) => {
        console.error(err);
        this.error = 'Error al cargar mascotas';
        this.cargando = false;
      }
    });
  }

  editarMascota(id: number) {
    this.router.navigate([`/mascotas/editar/${id}`]);
  }
}
