import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
// 1. CAMBIO: Importamos el AuthService (reactivo)
import { AuthService } from '../services/auth.service';
import { map, take } from 'rxjs/operators';
import { Observable } from 'rxjs';

/**
 * Este guardián protege las rutas que requieren que el usuario esté autenticado.
 * (Versión Reactiva)
 */
export const authGuard: CanActivateFn = (route, state) => {
  // 2. CAMBIO: Inyectamos el AuthService
  const authService = inject(AuthService);
  const router = inject(Router);

  // 3. CAMBIO: Escuchamos el observable user$
  return authService.user$.pipe(
    take(1), // Tomamos solo el valor actual
    map((user) => {
      if (user) {
        // Si hay un usuario en el estado, puede pasar
        return true;
      }

      // Si no hay usuario, redirigimos a login
      router.navigate(['/login']);
      return false;
    })
  ) as Observable<boolean>; // Aseguramos el tipo de retorno
};
