import { Component, ChangeDetectorRef, Input } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router, RouterLink } from "@angular/router"

import { Mascota, MascotaService } from "../../../services/mascota-service"
import { AuthService } from "../../../services/auth-service"
import { HomeHeader } from "../../home/home-header/home-header"

@Component({
  selector: "app-mis-mascotas",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, HomeHeader],
  templateUrl: "./mis-mascotas.html",
  styleUrls: ["./mis-mascotas.css"],
})
export class MisMascotas {
  @Input() modo: "usuario" | "perdidas" = "usuario"
  @Input() mostrarAcciones = true
  @Input() titulo = ""

  mascotasPaginadas: Mascota[] = []
  cargando = true
  error = ""

  paginaActual = 1
  mascotasPorPagina = 5
  totalPaginas = 1
  totalElementos = 0

  constructor(
    private mascotaService: MascotaService,
    private authService: AuthService,
    private router: Router,
    private cdRef: ChangeDetectorRef,
  ) { }

  ngOnInit() {
    // Establecer título por defecto si no se proporciona
    if (!this.titulo) {
      this.titulo = this.modo === "usuario" ? "Mis Mascotas" : "Mascotas Perdidas"
    }

    // Establecer mostrarAcciones según el modo si no se especificó
    if (this.modo === "perdidas" && this.mostrarAcciones === true) {
      this.mostrarAcciones = false
    }

    if (this.modo === "usuario") {
      const usuarioId = this.authService.getUsuarioId()

      if (!usuarioId) {
        this.error = "Usuario no autenticado"
        this.cargando = false
        return
      }

      this.listarMascotas(usuarioId)
    } else {
      this.listarMascotasPerdidas()
    }
  }

  listarMascotas(usuarioId: number) {
    this.cargando = true
    this.error = ""

    this.mascotaService.getMascotasPorUsuarioPaginado(usuarioId, this.paginaActual, this.mascotasPorPagina).subscribe({
      next: (res) => {
        this.mascotasPaginadas = res.content
        this.totalPaginas = res.totalPages
        this.totalElementos = res.totalElements || 0

        this.cargando = false
        this.cdRef.detectChanges()
      },
      error: (err) => {
        console.error(err)
        this.error = "Error al cargar tus mascotas"
        this.cargando = false
        this.cdRef.detectChanges()
      },
    })
  }

  listarMascotasPerdidas() {
    this.cargando = true
    this.error = ""

    this.mascotaService.getMascotasPerdidas(this.paginaActual, this.mascotasPorPagina).subscribe({
      next: (res) => {
        this.mascotasPaginadas = res.content
        this.totalPaginas = res.totalPages
        this.totalElementos = res.totalElements || 0
        this.cargando = false
        this.cdRef.detectChanges()
      },
      error: (err) => {
        console.error(err)
        this.error = "Error al cargar mascotas perdidas"
        this.cargando = false
        this.cdRef.detectChanges()
      },
    })
  }

  siguientePagina() {
    if (this.paginaActual < this.totalPaginas) {
      this.paginaActual++
      this.recargarDatos()
    }
  }

  paginaAnterior() {
    if (this.paginaActual > 1) {
      this.paginaActual--
      this.recargarDatos()
    }
  }

  recargarDatos() {
    if (this.modo === "usuario") {
      const usuarioId = this.authService.getUsuarioId()
      if (usuarioId) this.listarMascotas(usuarioId)
    } else {
      this.listarMascotasPerdidas()
    }
  }

  editarMascota(id: number) {
    this.router.navigate([`/mascotas/editar/${id}`])
  }

  eliminarMascota(id: number) {
    if (!confirm("¿Desea eliminar esta mascota?")) return

    this.mascotaService.deleteMascota(id).subscribe({
      next: () => {
        if (this.mascotasPaginadas.length === 1 && this.paginaActual > 1) {
          this.paginaActual--
        }

        const usuarioId = this.authService.getUsuarioId()
        if (usuarioId) this.listarMascotas(usuarioId)
      },
      error: (err) => {
        console.error(err)
        this.error = "Error al eliminar mascota"
      },
    })
  }

  paginasArray(): number[] {
    return Array.from({ length: this.totalPaginas }, (_, i) => i + 1)
  }

  irAPagina(numero: number) {
    this.paginaActual = numero
    this.recargarDatos()
  }

  verDetalle(id: number) {
    this.router.navigate([`/mascotas/detalle/${id}`]);
  }

  irAReportarAvistamiento(mascotaId: number) {
      this.router.navigate([`/mascotas/detalle/${mascotaId}/avistamientos/nuevo`]);
  }
}
