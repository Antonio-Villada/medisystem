// /services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
// Imports actualizados para el estado reactivo
import { Observable, tap, BehaviorSubject } from 'rxjs';
import { JwtStorageService } from './jwt-storage.service';
import { LoginRequest, LoginResponse } from '../models/auth.models';
// ¡Importante! Instala con: npm install jwt-decode
import { jwtDecode } from 'jwt-decode';
// Importamos environment (asumiendo que lo tienes configurado)
import { environment } from '../../../environments/environment';

// Datos del usuario que mantendremos en memoria
export interface UserData {
  username: string;
  roles: string[];
  idPaciente: string | null; // La cédula (si es paciente)
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // Usamos la variable de entorno para la URL de la API
  private readonly API_URL = environment.API_URL + '/auth/';

  // BehaviorSubject para manejar el estado del usuario de forma reactiva
  private user = new BehaviorSubject<UserData | null>(null);
  public user$ = this.user.asObservable();

  constructor(
    private http: HttpClient,
    private jwtStorage: JwtStorageService,
    private router: Router
  ) // Quitamos la dependencia de PacienteService, como acordamos en tu corrección
  {
    // Al cargar el servicio, intenta cargar al usuario si hay un token
    this.cargarUsuarioDesdeToken();
  }

  // Método para obtener el rol fácilmente
  public tieneRol(rol: string): boolean {
    return this.user.value?.roles.includes(rol) ?? false;
  }

  // Método para obtener el ID del paciente (cédula)
  public getIdPaciente(): string | null {
    return this.user.value?.idPaciente ?? null;
  }

  // (Temporalmente, hasta que se implemente la carga del ID del paciente)
  public setIdPaciente(id: string) {
    const currentUser = this.user.value;
    if (currentUser) {
      this.user.next({ ...currentUser, idPaciente: id });
    }
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.API_URL + 'login', credentials).pipe(
      tap((response) => {
        // 1. Guardar token en localStorage
        this.jwtStorage.guardarToken(response.jwtToken);
        // 2. Decodifica, extrae roles/username y actualiza el BehaviorSubject
        this.procesarToken(response.jwtToken);
        // 3. Redirige
        this.router.navigate(['/home']);
      })
    );
  }

  logout(): void {
    this.jwtStorage.limpiar();
    this.user.next(null); // Limpia el usuario del estado reactivo
    this.router.navigate(['/login']);
  }

  // --- Métodos de Ayuda (Nuevos) ---

  private cargarUsuarioDesdeToken(): void {
    const token = this.jwtStorage.obtenerToken();
    if (token) {
      this.procesarToken(token);
    }
  }

  // Procesa el token para extraer datos y actualizar el estado
  private procesarToken(token: string): void {
    try {
      // Usamos 'any' para flexibilidad, pero idealmente se define una interface
      const decodedToken: any = jwtDecode(token);

      const username = decodedToken.sub; // 'sub' (subject) es el username
      const roles = decodedToken.roles; // Tus roles (basado en tu backend)

      // Guardamos los datos del JWT en el estado
      // Como acordamos, idPaciente se queda nulo por ahora.
      this.user.next({
        username: username,
        roles: roles,
        idPaciente: null,
      });
    } catch (error) {
      console.error('Error decodificando el token (puede haber expirado)', error);
      // Si el token es inválido o expiró, cerramos la sesión
      this.logout();
    }
  }
}
