import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Especialidad } from '../models/especialidad.interface';
import { environment } from '../../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class EspecialidadService {

  private readonly API_URL = environment.API_URL + '/especialidades';

  constructor(private http: HttpClient) {}
  
  // Obtener todas las Especialidades
  getAll(): Observable<Especialidad[]> {
    return this.http.get<Especialidad[]>(this.API_URL);
  }
  getById(id: number): Observable<Especialidad> {
    return this.http.get<Especialidad>(`${this.API_URL}/${id}`);
  }
  // Crear una nueva Especialidad
  create(especialidad: Especialidad): Observable<Especialidad> {
    // no envía id_especialidad porque el backend lo genera automáticamente
    return this.http.post<Especialidad>(this.API_URL, especialidad);
  }
  // Actualizar una Especialidad existente
  update(id: number, especialidad: Especialidad): Observable<Especialidad> {
    return this.http.put<Especialidad>(`${this.API_URL}/${id}`, especialidad);
  }
  // Eliminar una Especialidad
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
