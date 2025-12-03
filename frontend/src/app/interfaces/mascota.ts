import { Coordenada } from "./coordenada";
import { EstadoMascota } from "./enums";

export interface Mascota {
    id: number;
    nombre: string;
    color: string;
    estado: EstadoMascota;
    fotos: string[];
    coordenada: Coordenada;
    nombrePublicador: string;
}

export interface MascotaCreateRequest {
    nombre: string;
    tamanio: number;
    color: string;
    descripcionExtra: string;
    fotos: string[];
    coordenada: Coordenada;
    publicadorId: number;
}

export interface MascotaUpdateRequest {
    nombre: string;
    tamanio: number;
    color: string;
    descripcionExtra: string;
    fotos: string[];
    coordenada: Coordenada;
}