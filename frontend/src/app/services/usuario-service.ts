import { Injectable } from '@angular/core';
import { environment } from '../../envaironment/envaironment';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../interfaces/usuario';
import { Observable } from 'rxjs';
import { AuthService } from './auth-service';

const API_BASE_URL = environment.apiUrl;
@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  constructor(private http: HttpClient, private authService: AuthService) { }
  getPerfil(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${API_BASE_URL}/usuarios/${id}`);
  }
  editarPerfil(id: number, usuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.put<Usuario>(`${API_BASE_URL}/usuarios/${id}`, usuario);
  }
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${API_BASE_URL}/usuarios`);
  }
  deleteUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${API_BASE_URL}/usuarios/${id}`);
  }
}
