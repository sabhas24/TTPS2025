import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsuarioLogin, JwtResponse, Usuario, UsuarioCreate } from '../interfaces/usuario';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../envaironment/envaironment';

const API_BASE_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'auth_token';

  // BehaviorSubject para notificar cambios de login
  private loggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn());
  public loggedIn$ = this.loggedInSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(credentials: UsuarioLogin): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${API_BASE_URL}/auth/login`, credentials).pipe(
      tap(response => {
        this.setToken(response.token);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.loggedInSubject.next(false); // notificamos que ya no está logueado
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
    this.loggedInSubject.next(true); // notificamos que ahora está logueado
  }

  // Registrarse y guardar token si la API devuelve JWT
  register(user: UsuarioCreate): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${API_BASE_URL}/usuarios/registrar`, user).pipe(
      tap(response => {
        if (response?.token) {
          this.setToken(response.token);
        }
      })
    );
  }
}
