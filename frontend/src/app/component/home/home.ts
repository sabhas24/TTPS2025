import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterModule } from "@angular/router"
import { HomeHeader } from "./home-header/home-header"
import { HomeHero } from "./home-hero/home-hero"
import { HomeFeatures } from "./home-features/home-features"
import { HomeCta } from "./home-cta/home-cta"
import { HomeFooter } from "./home-footer/home-footer"
import { MisMascotas } from "../mascota/mis-mascotas/mis-mascotas"

@Component({
    selector: "app-home",
    standalone: true,
    imports: [
        CommonModule,
        RouterModule,
        HomeHeader,
        HomeHero,
        HomeFeatures,
        HomeCta,
        HomeFooter,
        MisMascotas,
    ],
    templateUrl: "./home.html",
    styleUrl: "./home.css",
})
export class Home { }