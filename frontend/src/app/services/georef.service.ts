import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeorefService {

  private API_URL = 'https://apis.datos.gob.ar/georef/api';

  constructor(private http: HttpClient) {}

  obtenerCentroideLocalidad(nombre: string): Observable<{ lat: number; lon: number }> {
    return this.http
      .get<any>(`${this.API_URL}/localidades-censales`, {
        params: {
          nombre,
          max: 1
        }
      })
      .pipe(
        map(response => {
          const loc = response.localidades_censales?.[0];
          if (!loc) {
            throw new Error('Localidad no encontrada');
          }
          return {
            lat: loc.centroide.lat,
            lon: loc.centroide.lon
          };
        })
      );
  }
}
