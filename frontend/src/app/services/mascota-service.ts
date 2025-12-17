import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../envaironment/envaironment'; // Ajustar path según tu proyecto

// ===== Modelo completo de Mascota =====
export interface CoordenadaDTO {
  latitud: number;
  longitud: number;
  barrio?: string; // hacemos opcional por compatibilidad
}

export interface Mascota {
  id: number;
  nombre: string;
  tamanio: number;
  color: string;
  descripcionExtra: string;
  fotos: string[];
  coordenada: CoordenadaDTO;
  publicadorId: number;
  estado: string;
  nombrePublicador: string;
}

// ===== DTO para crear Mascota =====
export interface MascotaCreateDTO {
  nombre: string;
  tamanio: number;
  color: string;
  descripcionExtra: string;
  fotos: string[];
  coordenada: CoordenadaDTO; // aquí incluimos barrio opcional
  publicadorId: number;
}

@Injectable({
  providedIn: 'root'
})
export class MascotaService {

  private baseUrl = `${environment.apiUrl}/mascotas`;

  constructor(private http: HttpClient) { }

  // ===== Crear nueva mascota =====
  createMascota(mascota: MascotaCreateDTO): Observable<Mascota> {
    return this.http.post<Mascota>(this.baseUrl, mascota);
  }

  // ===== Listar mascotas del usuario autenticado =====
  getMascotasPorUsuario(usuarioId: number): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.baseUrl}/usuario/${usuarioId}`);
  }

  // ===== Listar mascotas perdidas =====
  getMascotasPerdidas(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.baseUrl}/perdidas`);
  }

  // ===== Editar mascota =====
  updateMascota(id: number, dto: any): Observable<Mascota> {
    return this.http.put<Mascota>(`${this.baseUrl}/${id}`, dto);
  }

  // ===== Cambiar estado de mascota =====
  updateEstado(id: number, estado: string): Observable<Mascota> {
    return this.http.patch<Mascota>(`${this.baseUrl}/${id}/estado?estado=${estado}`, null);
  }

  // ===== Borrado lógico de mascota =====
  deleteMascota(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // ===== Obtener mascota por ID =====
  getMascotaById(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${this.baseUrl}/${id}`);
  }
}
