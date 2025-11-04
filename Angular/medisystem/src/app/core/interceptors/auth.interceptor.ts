// src/app/core/interceptors/auth.interceptor.ts

import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core'; // Usamos inject() en lugar de constructor
import { JwtStorageService } from '../services/jwt-storage.service';

/**
 * Interceptor funcional (HttpInterceptorFn) para adjuntar el token JWT.
 * Esto es la forma recomendada en Angular 20.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // 1. Obtenemos el servicio usando inject()
  const jwtStorage = inject(JwtStorageService);
  const token = jwtStorage.obtenerToken();

  // 2. Si NO hay token o la llamada es para obtener el token (login),
  //    simplemente deja pasar la solicitud original.
  if (!token) {
    return next(req);
  }

  // 3. Si SÍ hay token, clonamos la solicitud y añadimos la cabecera
  const authReq = req.clone({
    headers: req.headers.set('Authorization', 'Bearer ' + token),
  });

  // 4. Dejamos pasar la solicitud MODIFICADA (con el token)
  return next(authReq);
};

// NOTA: Ya no exportamos 'authInterceptorProvider' ni la clase.
