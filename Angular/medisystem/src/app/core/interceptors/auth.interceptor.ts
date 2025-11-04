// auth.interceptor.ts

import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtStorageService } from '../services/jwt-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private jwtStorage: JwtStorageService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.jwtStorage.obtenerToken();
    let authReq = req;

    if (token != null) {
      // Clona la petición y añade el encabezado Authorization: Bearer <token>
      authReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + token),
      });
    }

    return next.handle(authReq);
  }
}

// Exportar como un provider para usar en la configuración
export const authInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true,
};
