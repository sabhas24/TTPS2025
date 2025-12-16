import { usuarioTipo } from "./enums";

/* =========================
   USUARIO (RESPUESTA BACK)
   ========================= */
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

/* =========================
   LOGIN (JWT)
   ========================= */
export interface UsuarioLogin {
  email: string;
  password: string;   // 🔥 CLAVE
}

/* =========================
   REGISTRO
   ========================= */
export interface UsuarioCreate {
  nombre: string;
  apellido: string;
  email: string;
  contraseña: string; // ✔️ se mantiene SOLO para registro
  telefono: string;
  barrio: string;
  ciudad: string;
  foto?: string;
  tipo: usuarioTipo;
}

/* =========================
   JWT RESPONSE
   ========================= */
export interface JwtResponse {
  token: string;
}
