// src/app/features/medicos/services/medico.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

// Importa la interfaz Medico (si está en otro archivo, ajusta la ruta)
import { Medico } from '../models/medico.interface';

@Injectable({
  providedIn: 'root',
})
export class MedicoService {
  // --- CORRECCIÓN CRÍTICA ---
  // La URL completa es la del environment + el recurso '/medicos'
  private readonly API_URL = environment.API_URL + '/medicos';

  constructor(private http: HttpClient) {}

  /**
   * Obtiene la lista completa de médicos (necesario para el dropdown de citas).
   * Corresponde a GET /sistema/api/v1/medicos
   */
  public getMedicosParaUI(): Observable<Medico[]> {
    return this.http.get<Medico[]>(this.API_URL);
  }

  // ... (otros métodos si existen)
}
