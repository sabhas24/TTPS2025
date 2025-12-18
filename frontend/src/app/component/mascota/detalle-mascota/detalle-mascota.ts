import { Component, type OnInit, inject, ChangeDetectorRef, AfterViewInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { ActivatedRoute, Router } from "@angular/router"
import { MascotaService, type Mascota } from "../../../services/mascota-service"
import { AvistamientoService } from "../../../services/avistamiento-service"
import { UsuarioService } from "../../../services/usuario-service"
import { AuthService } from "../../../services/auth-service"
import type { Usuario } from "../../../interfaces/usuario"
import { HomeHeader } from "../../home/home-header/home-header"
import { HomeFooter } from "../../home/home-footer/home-footer"
import * as L from 'leaflet'



interface Avistamiento {
  id: number
  descripcion: string
  fecha: string
  coordenada: {
    latitud: number
    longitud: number
    barrio?: string
  }
  mascotaId: number
  reportadorId: number
}

@Component({
  selector: "app-detalle-mascota",
  standalone: true,
  imports: [CommonModule, HomeFooter, HomeHeader],
  templateUrl: "./detalle-mascota.html",
  styleUrls: ["./detalle-mascota.css"],
})
export class DetalleMascota implements OnInit, AfterViewInit {
  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private mascotaService = inject(MascotaService)
  private avistamientoService = inject(AvistamientoService)
  private usuarioService = inject(UsuarioService)
  private authService = inject(AuthService)
  private cdr = inject(ChangeDetectorRef)

  mascota: Mascota | null = null
  dueno: Usuario | null = null
  avistamientos: Avistamiento[] = []
  selectedImageIndex = 0
  loading = true
  error = ""
  currentUser: any = null
  mapaLat = -34.6037
  mapaLon = -58.3816

  private map: L.Map | null = null
  private marker: L.Marker | null = null

  ngOnInit(): void {
    this.currentUser = this.authService.getUsuario()

    const id = this.route.snapshot.paramMap.get("id")
    if (id) {
      this.cargarMascota(Number(id))
    } else {
      this.error = "ID de mascota no válido"
      this.loading = false
    }
  }

  ngAfterViewInit(): void {
    // El mapa se inicializará cuando se cargue la mascota
  }

  inicializarMapa(): void {
    // Esperar a que el div del mapa exista en el DOM
    const mapDiv = document.getElementById('mapa-mascota');
    if (!mapDiv) {
      setTimeout(() => this.inicializarMapa(), 100);
      return;
    }

    // Configurar el ícono personalizado de Leaflet
    const iconDefault = L.icon({
      iconUrl: 'leaflet/marker-icon.png',
      shadowUrl: 'leaflet/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41]
    });


    if (this.map) {
      this.map.remove();
    }

    this.map = L.map('mapa-mascota').setView([this.mapaLat, this.mapaLon], 15);

    // Agregar capa de tiles (OpenStreetMap)
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);


    this.marker = L.marker([this.mapaLat, this.mapaLon], { icon: iconDefault })
      .addTo(this.map)
      .bindPopup(`
        <div style="text-align: center;">
          <strong>${this.mascota?.nombre}</strong><br>
          Última vez vista aquí
        </div>
      `)
      .openPopup();
  }

  cargarMascota(id: number): void {
    this.mascotaService.getMascotaById(id).subscribe({
      next: (mascota) => {
        console.log('Mascota cargada:', mascota);
        this.mascota = mascota
        if (mascota.coordenada) {
          this.mapaLat = mascota.coordenada.latitud
          this.mapaLon = mascota.coordenada.longitud
        }

        setTimeout(() => {
          this.inicializarMapa()
        }, 100)

        if (mascota.publicadorId) {
          this.cargarDueno(mascota.publicadorId)
        } else {
          console.warn('Mascota sin publicadorId');
          this.loading = false
        }

        this.cargarAvistamientos(id)
      },
      error: (err) => {
        console.error("Error al cargar mascota:", err)
        this.error = "No se pudo cargar la información de la mascota"
        this.loading = false
      },
    })
  }

  cargarDueno(usuarioId: number): void {
    console.log('Cargando dueño con ID:', usuarioId);
    this.usuarioService.getPerfil(usuarioId).subscribe({
      next: (usuario) => {
        console.log('Dueño cargado:', usuario);
        this.dueno = usuario
        this.loading = false
        this.cdr.detectChanges()
      },
      error: (err) => {
        console.error("Error al cargar dueño:", err)
        this.loading = false
        this.cdr.detectChanges()
      },
    })
  }

  cargarAvistamientos(mascotaId: number): void {
    this.avistamientoService.getAvistamientosPorMascota(mascotaId).subscribe({
      next: (avistamientos) => {
        this.avistamientos = avistamientos.sort((a: Avistamiento, b: Avistamiento) => {
          return new Date(b.fecha).getTime() - new Date(a.fecha).getTime()
        })
      },
      error: (err) => {
        console.error("Error al cargar avistamientos:", err)
      },
    })
  }

  selectImage(index: number): void {
    this.selectedImageIndex = index
  }

  getEstadoBadgeClass(): string {
    if (!this.mascota) return ""
    return this.mascota.estado === "PERDIDO" ? "badge-perdido" : "badge-encontrado"
  }

  getEstadoTexto(): string {
    if (!this.mascota) return ""
    return this.mascota.estado === "PERDIDO" ? "Perdido" : "Encontrado"
  }

  getDuenoCiudad(): string {
    if (!this.dueno) return ""
    return this.dueno.barrio ? `${this.dueno.barrio}, ${this.dueno.ciudad}` : this.dueno.ciudad
  }

  getTamanioTexto(): string {
    if (!this.mascota) return ""
    const tamanios: { [key: number]: string } = {
      1: "Pequeño",
      2: "Mediano",
      3: "Grande",
    }
    return tamanios[this.mascota.tamanio] || "Desconocido"
  }

  formatearFecha(fecha: string): string {
    const date = new Date(fecha)
    const options: Intl.DateTimeFormatOptions = {
      day: "numeric",
      month: "long",
      year: "numeric",
    }
    return date.toLocaleDateString("es-ES", options)
  }

  formatearFechaAvistamiento(fecha: string): string {
    const date = new Date(fecha)
    const options: Intl.DateTimeFormatOptions = {
      day: "numeric",
      month: "short",
      hour: "2-digit",
      minute: "2-digit",
    }
    return date.toLocaleDateString("es-ES", options)
  }

  reportarAvistamiento(): void {
    if (!this.mascota) return

    alert("Funcionalidad de reportar avistamiento en desarrollo")
  }

  volverAtras(): void {
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigate(["/"])
    }
  }

  getDuenoInitial(): string {
    if (!this.dueno) return "U"
    return this.dueno.nombre.charAt(0).toUpperCase()
  }
}
