// medico-api.repository.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medico } from '../models/medico.interface'; // Importamos el modelo
// Opcionalmente puedes usar catchError para un manejo centralizado
// import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root', // Disponible en toda la aplicación
})
export class MedicoApiRepository {
  // Asegúrate de usar el puerto y endpoint correctos de tu MediSystem (Spring Boot)
  private readonly API_URL = 'http://localhost:8080/api/v1/medicos';

  constructor(private http: HttpClient) {}

  /**
   * Obtiene la lista de médicos desde el backend de Spring Boot.
   * La respuesta es tipada gracias al <Medico[]>.
   */
  obtenerTodos(): Observable<Medico[]> {
    // Si tienes Spring Boot Security configurado, aquí podrías añadir el token JWT
    return this.http.get<Medico[]>(this.API_URL);
    // .pipe(
    //   catchError(this.handleError) // Si tienes un servicio de manejo de errores
    // );
  }

  // Podrías añadir otros métodos como obtenerPorId(), crear(), actualizar(), etc.
}
