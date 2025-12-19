import { Component, OnInit } from '@angular/core';
import { Avistamiento, AvistamientoResponse } from '../../../interfaces/avistamiento';
import { AvistamientoService } from '../../../services/avistamiento-service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-avistamiento-list',
  templateUrl: './avistamiento-list.component.html',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe
  ]
})
export class AvistamientoListComponent implements OnInit {
  avistamientos: AvistamientoResponse[] = [];
  mascotaId!: number;
  cargando = true;

  constructor(
    private service: AvistamientoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.parent!.paramMap.subscribe(params => {
      this.mascotaId = Number(params.get('mascotaId'));
      this.cargarAvistamientos();
    });
  }

  private cargarAvistamientos() {
    this.cargando = true;
    this.service.getAvistamientosPorMascota(this.mascotaId).subscribe({
      next: (avistamientos) => {
        this.cargando = false;
        this.avistamientos = avistamientos.sort((a: AvistamientoResponse, b: AvistamientoResponse) => {
          return new Date(b.fecha).getTime() - new Date(a.fecha).getTime()
        })
      },
      error: (err) => {
        this.cargando = false;
        console.error("Error al cargar avistamientos:", err)
      }  
    });
  }


  verDetalle(id: number) {
    this.router.navigate([id], { relativeTo: this.route });
  }

  nuevoAvistamiento() {
    this.router.navigate(['nuevo'], { relativeTo: this.route });
  }
}
