import { Injectable } from '@angular/core';
import { FormulaApiRepository } from '../data/formula-api.repository';
import { Observable, map, of } from 'rxjs';
import { Formula } from '../models/formula.model';
// Importamos el AuthService actualizado
import { AuthService } from '../../../core/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class FormulaService {
  constructor(
    private api: FormulaApiRepository,
    private authService: AuthService // Inyectamos AuthService para leer el rol
  ) {}

  /**
   * Método de negocio principal.
   * Obtiene las fórmulas relevantes para el usuario actual.
   */
  getMisFormulas(): Observable<Formula[]> {
    // --- INICIO DE LA CORRECCIÓN ---
    // Ya no usamos 'getRolesFromToken()'.
    // Ahora usamos los helpers 'tieneRol()' de nuestro AuthService reactivo.

    // 1. Si es Admin o Medico, llama a /formulas
    if (this.authService.tieneRol('ADMINISTRADOR') || this.authService.tieneRol('MEDICO')) {
      return this.api.getFormulas();
    }

    // 2. Si es Paciente, llama a /citas y extrae las fórmulas
    if (this.authService.tieneRol('PACIENTE')) {
      return this.api.getCitas().pipe(
        map(
          (citas) =>
            citas
              .filter((cita) => cita.formula != null) // Filtra solo citas que tengan fórmula
              .map((cita) => cita.formula as Formula) // Extrae la fórmula
        )
      );
    }
    // --- FIN DE LA CORRECCIÓN ---

    // 3. Si no tiene rol (o error), devuelve vacío
    return of([]); // Observable de un array vacío
  }
}
