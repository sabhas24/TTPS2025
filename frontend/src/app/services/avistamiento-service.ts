import { Injectable } from '@angular/core';
import { environment } from '../../envaironment/envaironment';
import { HttpClient } from '@angular/common/http';
import { Mascota } from '../interfaces/mascota';
import { Observable } from 'rxjs';
const API_URL = environment.apiUrl;
@Injectable({
  providedIn: 'root',
})
export class AvistamientoService {
  constructor(private http: HttpClient) { }
  getAvistamientosPorMascota(mascotaId: number): Observable<any> {
    const url = `${environment.apiUrl}/avistamientos/mascota/${mascotaId}`;
    return this.http.get<any>(url);

  }
  createAvistamiento(data: any): Observable<any> {
    const url = `${environment.apiUrl}/avistamientos`;
    return this.http.post<any>(url, data);
  }
  deleteAvistamiento(avistamientoId: number): Observable<any> {
    const url = `${environment.apiUrl}/avistamientos/${avistamientoId}`;
    return this.http.delete<any>(url);
  }
  updateAvistamiento(avistamientoId: number, data: any): Observable<any> {
    const url = `${environment.apiUrl}/avistamientos/${avistamientoId}`;
    return this.http.put<any>(url, data);
  }
  getAllAvistamientos(): Observable<any> {
    const url = `${environment.apiUrl}/avistamientos`;
    return this.http.get<any>(url);
  }
}
