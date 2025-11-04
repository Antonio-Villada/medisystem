// src/app/features/citas/services/citas.service.ts

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { CitaRequest, CitaResponse } from '../models/cita.models';

@Injectable({
  providedIn: 'root',
})
export class CitasService {
  // 1. Apunta a la URL base de tu API + el controlador de Citas
  private readonly API_URL = environment.API_URL + '/citas';

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todas las citas (filtrado por rol se hace en backend)
   * Corresponde a: @GetMapping
   [cite_start]* [cite: 191-193]
   */
  public getAllCitas(): Observable<CitaResponse[]> {
    return this.http.get<CitaResponse[]>(this.API_URL);
  }

  /**
   * Agenda una nueva cita
   * Corresponde a: @PostMapping
   [cite_start]* [cite: 186-189]
   */
  public agendarCita(dto: CitaRequest): Observable<CitaResponse> {
    return this.http.post<CitaResponse>(this.API_URL, dto);
  }

  /**
   * Actualiza una cita (completa)
   * Corresponde a: @PutMapping("/{id}")
   [cite_start]* [cite: 200-203]
   */
  public updateCita(id: number, dto: CitaRequest): Observable<CitaResponse> {
    return this.http.put<CitaResponse>(`${this.API_URL}/${id}`, dto);
  }

  /**
   * Cancela (borra) una cita
   * Corresponde a: @DeleteMapping("/{id}")
   [cite_start]* [cite: 213-215]
   */
  public deleteCita(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }

  // Nota: getCitaById y patchCita se pueden añadir si se necesitan
}
