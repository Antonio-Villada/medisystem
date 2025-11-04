// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login.component';
import { HomeComponent } from './pages/home/home.component';
import { CitasComponent } from './pages/citas/citas.component';
import { OrdenesComponent } from './pages/ordenes/ordenes.component';

// --- PASO 1: IMPORTA TU NUEVO GUARDIÁN ---
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  // Rutas públicas (no llevan guardián)
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  // --- PASO 2: APLICA EL GUARDIÁN A TUS RUTAS PRIVADAS ---

  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard], // <-- AÑADIDO
  },
  {
    path: 'Citas',
    component: CitasComponent,
    canActivate: [authGuard], // <-- AÑADIDO
  },
  {
    path: 'Ordenes',
    component: OrdenesComponent,
    canActivate: [authGuard], // <-- AÑADIDO
  },

  // Ruta comodín (pública)
  { path: '**', redirectTo: 'login' },
];
