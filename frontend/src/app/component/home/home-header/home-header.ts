
import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"

interface NavLink {
  label: string
  path: string
}


@Component({
  selector: 'app-home-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-header.html',
  styleUrl: './home-header.css',
})
export class HomeHeader {
  navLinks: NavLink[] = [
    { label: "Inicio", path: "/" },
    { label: "Ranking", path: "/ranking" },
    { label: "Mascotas Perdidas", path: "/mascotas-perdidas" },
  ]
}
