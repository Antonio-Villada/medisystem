// /features/pacientes/services/paciente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

// (Debes crear esta interfaz basada en tu PacienteResponseDTO [cite: 907-908])
export interface Paciente {
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
   * Llama al nuevo endpoint GET /pacientes/perfil
   */
  getMiPerfil(): Observable<Paciente> {
    return this.http.get<Paciente>(`${this.API_URL}/perfil`);
  }
}
