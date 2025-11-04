// jwt-storage.service.ts

import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root',
})
export class JwtStorageService {
  constructor() {}

  limpiar(): void {
    window.localStorage.removeItem(TOKEN_KEY);
  }

  public guardarToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public obtenerToken(): string | null {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  public estaAutenticado(): boolean {
    return !!this.obtenerToken();
  }
}
