import { Injectable } from '@angular/core';
import { environment } from '../../envaironment/envaironment';
import { HttpClient } from '@angular/common/http';
import { Mascota } from '../interfaces/mascota';
import { Observable } from 'rxjs';

const API_BASE_URL = environment.apiUrl;
@Injectable({
  providedIn: 'root',
})
export class MascotaService {
  constructor(private http: HttpClient) { }
  getMascotaById(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${API_BASE_URL}/mascotas/${id}`);
  }
  deleteMascota(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/mascotas/${id}`);
  }
  createMascota(mascota: Mascota): Observable<Mascota> {
    return this.http.post<Mascota>(`${API_BASE_URL}/mascotas`, mascota)
  }
  updateMascota(id: number, mascota: Partial<Mascota>): Observable<Mascota> {
    return this.http.put<Mascota>(`${API_BASE_URL}/mascotas/${id}`, mascota);
  }
  updateEstado(id: number, estadoAdopcion: string): Observable<Mascota> {
    return this.http.patch<Mascota>(`${API_BASE_URL}/mascotas/${id}/estado`, { estadoAdopcion });
  }
  getMascotasPerdiadas(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${API_BASE_URL}/mascotas/perdidas`);
  }
  getMascotasPorUsuario(usuarioId: number): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${API_BASE_URL}/mascotas/usuario/${usuarioId}`);
  }
}

