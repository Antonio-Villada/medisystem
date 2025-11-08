import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models/medicamento.interface';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {

  private readonly API_URL = environment.API_URL + '/medicamentos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Medicamento[]> {
    return this.http.get<Medicamento[]>(this.API_URL);
  }

  getById(id: number): Observable<Medicamento> {
    return this.http.get<Medicamento>(`${this.API_URL}/${id}`);
  }
  create(medicamento: Medicamento): Observable<Medicamento> {
    return this.http.post<Medicamento>(this.API_URL, medicamento);
  }
  update(id: number, medicamento: Medicamento): Observable<Medicamento> {
    return this.http.put<Medicamento>(`${this.API_URL}/${id}`, medicamento);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
