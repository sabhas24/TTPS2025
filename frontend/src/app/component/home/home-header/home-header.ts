import { Component, inject, HostListener, OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router, RouterModule } from "@angular/router"
import { AuthService } from "../../../services/auth-service"
import { UsuarioService } from "../../../services/usuario-service"
import type { Usuario } from "../../../interfaces/usuario"

interface NavLink {
  label: string
  path: string
  requiereLogin?: boolean
}

@Component({
  selector: "app-home-header",
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: "./home-header.html",
  styleUrls: ["./home-header.css"],
})
export class HomeHeader implements OnInit {
  authService = inject(AuthService)
  router = inject(Router)
  private usuarioService = inject(UsuarioService)

  isUserMenuOpen = false

  // Foto de perfil del usuario logueado
  usuarioFoto: string | null = null

  navLinks: NavLink[] = [
    { label: "Inicio", path: "/" },
    { label: "Mascotas Perdidas", path: "/mascotas/perdidas" },
    { label: "Registrar Mascota Perdida", path: "/mascotas/crear", requiereLogin: true },
    { label: "Mis Mascotas", path: "/mascotas/mis-mascotas", requiereLogin: true },
  ]

  ngOnInit(): void {
    // Cuando haya un usuario logueado, traemos su perfil completo para obtener la foto
    this.authService.usuario$.subscribe((usuarioToken) => {
      if (!usuarioToken) {
        this.usuarioFoto = null
        return
      }

      this.usuarioService.getPerfil(usuarioToken.id).subscribe({
        next: (usuario: Usuario) => {
          this.usuarioFoto = usuario.foto || null
        },
        error: () => {
          this.usuarioFoto = null
        },
      })
    })
  }

  getUserInitial(): string {
    const usuario = this.authService.getUsuario()
    if (usuario && usuario.nombre) {
      return usuario.nombre.charAt(0).toUpperCase()
    }
    return "?"
  }

  toggleUserMenu() {
    this.isUserMenuOpen = !this.isUserMenuOpen
  }

  closeUserMenu() {
    this.isUserMenuOpen = false
  }

  @HostListener("document:click", ["$event"])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement
    const userMenuContainer = target.closest(".user-menu-container")

    if (!userMenuContainer && this.isUserMenuOpen) {
      this.isUserMenuOpen = false
    }
  }

  logout() {
    this.authService.logout()
    this.closeUserMenu()
    this.router.navigate(["/"])
  }
}
