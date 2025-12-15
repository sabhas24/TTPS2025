import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"


interface Feature {
  icon: string
  title: string
  description: string
}
@Component({
  selector: 'app-home-features',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-features.html',
  styleUrl: './home-features.css',
})
export class HomeFeatures {
  features: Feature[] = [
    {
      icon: "search",
      title: "Buscar Mascotas Perdidas",
      description: "Busca fácilmente mascotas perdidas en tu área usando nuestros filtros de búsqueda avanzados.",
    },
    {
      icon: "report",
      title: "Reportar una Mascota Perdida",
      description:
        "Reporta una mascota perdida con información detallada y fotos para aumentar las posibilidades de encontrarla.",
    },
    {
      icon: "community",
      title: "Conectar con la Comunidad",
      description:
        "Únete a nuestro foro comunitario para compartir información, consejos y apoyo con otros dueños de mascotas.",
    },
  ]

}
