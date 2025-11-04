// src/app/features/pacientes/services/paciente.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

/**
 * Interface que define la estructura del PacienteResponseDTO del backend.
 * Nota: Ya no se llama "Paciente", sino "PacientePerfil" para evitar conflictos
 * si en el futuro necesitas una interfaz Paciente diferente.
 */
export interface PacientePerfil {
  // <-- Corregido: Ahora se llama PacientePerfil
  idPaciente: string;
  nombrePaciente: string;
  ciudad: string;
  correo: string;
  nombreEps: string;
  username: string;
  telefonos: string[];
}

@Injectable({
  providedIn: 'root',
})
export class PacienteService {
  private readonly API_URL = environment.API_URL + '/pacientes';

  constructor(private http: HttpClient) {}

  /**
   * Llama al endpoint GET /pacientes/perfil (que ya existe en tu Spring Boot).
   */
  getMiPerfil(): Observable<PacientePerfil> {
    // <-- Usamos PacientePerfil aquí
    // La URL correcta que coincide con tu @GetMapping("/perfil")
    return this.http.get<PacientePerfil>(`${this.API_URL}/perfil`);
  }
}
