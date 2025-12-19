import { Injectable } from '@angular/core';
import { environment } from '../../envaironment/envaironment';
import { HttpClient } from '@angular/common/http';
import { Mascota } from '../interfaces/mascota';
import { Observable } from 'rxjs';
import {
  AvistamientoCreateRequest,
  AvistamientoUpdateRequest,
  AvistamientoResponse
} from '../interfaces/avistamiento';const API_URL = environment.apiUrl;


@Injectable({
  providedIn: 'root',
})
export class AvistamientoService {
  constructor(private http: HttpClient) { }
  getAvistamientosPorMascota(mascotaId: number): Observable<AvistamientoResponse[]> {
    const url = `${environment.apiUrl}/avistamientos/mascota/${mascotaId}`;
    return this.http.get<AvistamientoResponse[]>(url);

  }

  createAvistamiento(avistamiento: AvistamientoCreateRequest): Observable<AvistamientoResponse> {
    const url = `${environment.apiUrl}/avistamientos`;
    return this.http.post<AvistamientoResponse>(url, avistamiento);
  }

  deleteAvistamiento(avistamientoId: number): Observable<void> {
    const url = `${environment.apiUrl}/avistamientos/${avistamientoId}`;
    return this.http.delete<void>(url);
  }

  updateAvistamiento(avistamientoId: number, avistamiento: AvistamientoUpdateRequest): Observable<AvistamientoResponse> {
    const url = `${environment.apiUrl}/avistamientos/${avistamientoId}`;
    return this.http.put<AvistamientoResponse>(url, avistamiento);
  }

  getAllAvistamientos(): Observable<AvistamientoResponse[]> {
    const url = `${environment.apiUrl}/avistamientos`;
    return this.http.get<AvistamientoResponse[]>(url);
  }

  obtenerDetalle(avistamientoId: number): Observable<AvistamientoResponse> {
    const url = `${environment.apiUrl}/avistamientos/${avistamientoId}`;
    return this.http.get<AvistamientoResponse>(url);
  }
}
