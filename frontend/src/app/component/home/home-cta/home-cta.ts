import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"


@Component({
  selector: 'app-home-cta',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-cta.html',
  styleUrl: './home-cta.css',
})
export class HomeCta { }
