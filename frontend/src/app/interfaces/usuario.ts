import { usuarioTipo } from "./enums";
export interface Usuario {
    id: number;
    nombre: string;
    apellido: string;
    email: string;
    tipo: usuarioTipo;

}
export interface Usuario {
    id: number;
    nombre: string;
    apellido: string;
    email: string;
    telefono: string;
    barrio: string;
    ciudad: string;
    foto?: string;
    tipo: usuarioTipo;
}

export interface UsuarioLogin {
    email: string;
    contraseña: string;
}

export interface UsuarioCreate {
    nombre: string;
    apellido: string;
    email: string;
    contraseña: string;
    telefono: string;
    barrio: string;
    ciudad: string;
    foto?: string;
    tipo: usuarioTipo;
}
export interface JwtResponse {
    token: string;
}