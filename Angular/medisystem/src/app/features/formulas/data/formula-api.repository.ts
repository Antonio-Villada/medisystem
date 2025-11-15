import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cita, Formula } from '../models/formula.model';

@Injectable({
  providedIn: 'root',
})
export class FormulaApiRepository {
  // URLs basadas en tu context-path y controllers
  private readonly API_FORMULAS = 'http://localhost:8080/sistema/api/v1/formulas';
  private readonly API_CITAS = 'http://localhost:8080/sistema/api/v1/citas';

  constructor(private http: HttpClient) {}

  /**
   * Llama a GET /formulas
   * (Tu backend filtra por rol Admin/Medico)
   */
  getFormulas(): Observable<Formula[]> {
    return this.http.get<Formula[]>(this.API_FORMULAS);
  }

  /**
   * Llama a GET /citas
   * (Tu backend filtra por rol Paciente)
   */
  getCitas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(this.API_CITAS);
  }

  /**
   * Llama a GET /formulas/{id}
   */
  getFormulaById(id: number): Observable<Formula> {
    return this.http.get<Formula>(`${this.API_FORMULAS}/${id}`);
  }
}
