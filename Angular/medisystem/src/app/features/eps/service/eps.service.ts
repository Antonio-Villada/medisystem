import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Eps } from '../models/eps.interface';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EpsService {

  private readonly API_URL = environment.API_URL + '/eps';

  constructor(private http: HttpClient) {}

  // Obtener todas las EPS
  getAll(): Observable<Eps[]> {
    return this.http.get<Eps[]>(this.API_URL);
  }

  // Obtener una EPS por su ID
  getById(id: number): Observable<Eps> {
    return this.http.get<Eps>(`${this.API_URL}/${id}`);
  }

  // Crear una nueva EPS
  create(eps: Eps): Observable<Eps> {
    // no envía id_eps porque el backend lo genera automáticamente
    return this.http.post<Eps>(this.API_URL, eps);
  }

  // Actualizar una EPS existente
  update(id: number, eps: Eps): Observable<Eps> {
    return this.http.put<Eps>(`${this.API_URL}/${id}`, eps);
  }

  // Eliminar una EPS
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
