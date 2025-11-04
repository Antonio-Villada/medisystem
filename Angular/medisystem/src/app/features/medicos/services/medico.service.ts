// medico.service.ts

import { Injectable } from '@angular/core';
import { MedicoApiRepository } from '../data/medico-api.repository';
import { Medico } from '../models/medico.interface';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MedicoService {
  constructor(private apiRepository: MedicoApiRepository) {}

  /**
   * Obtiene la lista de médicos y aplica una lógica de negocio.
   * Por ejemplo: si quieres ordenar la lista o filtrar datos sensibles antes de enviarlos a la UI.
   */
  getMedicosParaUI(): Observable<Medico[]> {
    // Lógica 1: Llamar al repositorio (capa inferior)
    return this.apiRepository.obtenerTodos().pipe(
      // Lógica 2: Aquí aplicamos lógica de negocio específica del frontend
      map((medicos) =>
        medicos
          // Ejemplo de lógica: Filtrar médicos inactivos (si el DTO lo tuviera)
          // .filter(m => m.activo)
          // Ejemplo de lógica: Ordenar alfabéticamente
          .sort((a, b) => a.nombreMedico.localeCompare(b.nombreMedico))
      )
    );
  }
}
