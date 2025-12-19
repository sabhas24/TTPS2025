import { Coordenada } from "./coordenada";

export interface Avistamiento {
  id?: number;
  mascotaId: number;
  usuarioId: number;
  coordenada?: Coordenada;
  fecha?: string;
  comentario?: string;
  fotos?: string[];
  enPosesion?: boolean;
}

export interface AvistamientoCreateRequest {
  mascotaId: number;
  usuarioId: number;
  coordenada?: Coordenada;
  fecha?: string;
  comentario?: string;
  foto?: string[];     
  enPosesion?: boolean;
}

export interface AvistamientoUpdateRequest {
  comentario?: string;
  fotos?: string[];
  enPosesion?: boolean;
  coordenada?: Coordenada;
  habilitado?: boolean;
}

export interface AvistamientoResponse {
  id: number;
  fecha: string;
  coordenada?: Coordenada;
  comentario?: string;
  fotos?: string[];
  enPosesion: boolean;
  mascotaId: number;
  usuarioId: number;
  habilitado: boolean;
}