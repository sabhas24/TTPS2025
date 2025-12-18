import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsuarioLogin, JwtResponse, UsuarioCreate } from '../interfaces/usuario';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../envaironment/envaironment';
import { jwtDecode } from 'jwt-decode';

const API_BASE_URL = environment.apiUrl;

interface UsuarioToken {
  id: number;
  nombre: string;
  apellido: string;
  email: string;
  barrio: string;
  ciudad: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'auth_token';

  private loggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn());
  public loggedIn$ = this.loggedInSubject.asObservable();

  private usuarioSubject = new BehaviorSubject<UsuarioToken | null>(null);
  public usuario$ = this.usuarioSubject.asObservable();

  constructor(private http: HttpClient) {
    // 🔹 Inicializamos usuarioSubject desde el token si existe
    const usuario = this.getUsuarioDesdeToken();
    if (usuario) {
      this.usuarioSubject.next(usuario);
      this.loggedInSubject.next(true);
    }
  }

  login(credentials: UsuarioLogin): Observable<JwtResponse> {
    return this.http
      .post<JwtResponse>(`${API_BASE_URL}/auth/login`, credentials)
      .pipe(
        tap(response => {
          this.setToken(response.token);
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.usuarioSubject.next(null);
    this.loggedInSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  register(user: UsuarioCreate): Observable<JwtResponse> {
    return this.http
      .post<JwtResponse>(`${API_BASE_URL}/usuarios/registrar`, user)
      .pipe(
        tap(response => {
          if (response?.token) {
            this.setToken(response.token);
          }
        })
      );
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    this.usuarioSubject.next(this.getUsuarioDesdeToken());
    this.loggedInSubject.next(true);
  }

  private getUsuarioDesdeToken(): UsuarioToken | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      return jwtDecode<UsuarioToken>(token);
    } catch (error) {
      console.error('Error decodificando token', error);
      return null;
    }
  }

  getUsuario(): UsuarioToken | null {
    return this.usuarioSubject.value;
  }

  getNombreCompleto(): string {
    const u = this.usuarioSubject.value;
    return u ? `${u.nombre} ${u.apellido}` : '';
  }

  getUsuarioId(): number | null {
    return this.usuarioSubject.value?.id ?? null;
  }
}
