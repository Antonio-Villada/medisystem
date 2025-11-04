// src/app/core/guards/role.guard.ts

import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

// 1. Definimos un tipo basado en Enum de Spring Boot
type RolPermitido = 'ADMINISTRADOR' | 'MEDICO' | 'PACIENTE';

/**
 * Esta es una "fábrica" de guardianes.
 * Toma un array de roles permitidos y DEVUELVE la función CanActivateFn.
 */
export function roleGuard(allowedRoles: RolPermitido[]): CanActivateFn {
  // 2. Esto de adentro es el guardián real
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    // 3. Revisamos si el usuario tiene AL MENOS UNO de los roles permitidos
    // El método .some() se detiene y devuelve 'true' en el primer rol que coincida.
    const tienePermiso = allowedRoles.some((rol) => authService.tieneRol(rol));

    if (tienePermiso) {
      // Si tiene el rol, puede continuar
      return true;
    }

    // 4. Si no tiene permiso, lo redirigimos a la página 'home'
    // (o a una página de 'acceso-denegado' si prefieres)
    router.navigate(['/home']);
    return false;
  };
}
