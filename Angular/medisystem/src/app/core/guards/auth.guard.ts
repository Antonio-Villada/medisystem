// src/app/core/guards/auth.guard.ts

import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { JwtStorageService } from '../services/jwt-storage.service';

/**
 * Este guardián protege las rutas que requieren que el usuario esté autenticado.
 * (Versión simple)
 */
export const authGuard: CanActivateFn = (route, state) => {
  // 1. Obtenemos los servicios que necesitamos (el de JWT y el Router)
  const jwtStorage = inject(JwtStorageService);
  const router = inject(Router);

  // 2. Usamos el método que ya tenías en JwtStorageService
  if (jwtStorage.estaAutenticado()) {
    // Si tiene un token, puede continuar a la ruta
    return true;
  }

  // 3. Si no tiene token, lo redirigimos a la página de login
  router.navigate(['/login']);
  return false;
};
