// /services/auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, BehaviorSubject, switchMap, of } from 'rxjs'; // Imports actualizados
import { JwtStorageService } from './jwt-storage.service';
import { LoginRequest, LoginResponse } from '../models/auth.models';
import { jwtDecode } from 'jwt-decode'; // <-- ¡Importante! Instala con: npm install jwt-decode
import { PacientePerfil,PacienteService,} from '../../features/pacientes/services/paciente.service';
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
  private readonly API_URL = environment.API_URL + '/auth/';

  // BehaviorSubject para manejar el estado del usuario
  private user = new BehaviorSubject<UserData | null>(null);
  public user$ = this.user.asObservable();

  constructor(
    private http: HttpClient,
    private jwtStorage: JwtStorageService,
    private router: Router,
    private pacienteService: PacienteService // Inyectamos el servicio de paciente
  ) {
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

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.API_URL + 'login', credentials).pipe(
      tap((response) => {
        this.jwtStorage.guardarToken(response.jwtToken);
        // Decodifica y guarda los datos del usuario
        this.procesarToken(response.jwtToken);
        this.router.navigate(['/home']);
      })
    );
  }

  logout(): void {
    this.jwtStorage.limpiar();
    this.user.next(null); // Limpia el usuario
    this.router.navigate(['/login']);
  }

  // --- Métodos de Ayuda (Nuevos) ---

  private cargarUsuarioDesdeToken(): void {
    const token = this.jwtStorage.obtenerToken();
    if (token) {
      this.procesarToken(token);
    }
  }

  // src/app/core/services/auth.service.ts

  private procesarToken(token: string): void {
    try {
      const decodedToken: any = jwtDecode(token);
      const username = decodedToken.sub;
      const roles = decodedToken.roles; // Tus roles [cite: 1360]

      // --- INICIO DE LA CORRECCIÓN ---
      // Ya no hacemos la llamada a pacienteService.getMiPerfil() AQUÍ.
      // Simplemente guardamos los roles, igual que hacemos con ADMIN y MEDICO.
      // Dejaremos la búsqueda del idPaciente para cuando entremos a la página de Citas.

      this.user.next({
        username: username,
        roles: roles,
        idPaciente: null, // Lo dejaremos nulo POR AHORA
      });

      // --- FIN DE LA CORRECCIÓN ---
    } catch (error) {
      console.error('Error decodificando el token', error);
      this.logout();
    }
  }
}
