// src/app/app.config.ts

import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

// --- PASO 1: IMPORTAR withInterceptors ---
import { provideHttpClient, withInterceptors } from '@angular/common/http';
// --- PASO 2: IMPORTAR LA FUNCIÓN INTERCEPTOR ---
import { authInterceptor } from './core/interceptors/auth.interceptor'; // Importa la función, no el provider

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),

    // --- PASO 3: REGISTRO MODERNO DE HTTP CON INTERCEPTORES ---
    provideHttpClient(
      withInterceptors([authInterceptor]) // <-- AQUÍ REGISTRAMOS LA FUNCIÓN
    ),
  ],
};
