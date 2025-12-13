import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsuarioLogin, JwtResponse, Usuario, UsuarioCreate} from '../interfaces/usuario';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

const API_BASE_URL = 'http://localhost:8080';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'auth_token';

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
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  private setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }
  register(user: UsuarioCreate): Observable<Usuario> {
    return this.http.post<Usuario>(`${API_BASE_URL}/usuarios/registrar`, user);
  }
}
