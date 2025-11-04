// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login.component';
import { HomeComponent } from './pages/home/home.component';
import { CitasComponent } from './pages/citas/citas.component';
import { OrdenesComponent } from './pages/ordenes/ordenes.component';

// --- PASO 1: IMPORTA AMBOS GUARDIANES ---
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard'; // <-- AÑADIDO

export const routes: Routes = [
  // Rutas públicas
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },

  // --- PASO 2: APLICA AMBOS GUARDIANES A LAS RUTAS PRIVADAS ---

  // /home solo necesita estar autenticado
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard],
  },

  // /Citas necesita estar autenticado Y tener uno de los 3 roles
  {
    path: 'Citas',
    component: CitasComponent,
    // El array 'canActivate' puede tener múltiples guardianes.
    // Se ejecutan en orden.
    canActivate: [
      authGuard,
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']), // <-- AÑADIDO
    ],
  },

  // /Ordenes también necesita estar autenticado Y tener un rol
  {
    path: 'Ordenes',
    component: OrdenesComponent,
    canActivate: [
      authGuard,
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']), // <-- AÑADIDO
    ],
  },

  // Ruta comodín
  { path: '**', redirectTo: 'login' },
];
