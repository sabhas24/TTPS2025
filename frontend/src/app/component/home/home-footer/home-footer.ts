import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
interface FooterLink {
  label: string
  path: string
}

@Component({
  selector: 'app-home-footer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-footer.html',
  styleUrl: './home-footer.css',
})
export class HomeFooter {
  currentYear = new Date().getFullYear()

  footerLinks: FooterLink[] = [
    { label: "Acerca de Nosotros", path: "/acerca" },
    { label: "Contacto", path: "/contacto" },
    { label: "Política de Privacidad", path: "/privacidad" },
    { label: "Términos del Servicio", path: "/terminos" },
  ]
}
