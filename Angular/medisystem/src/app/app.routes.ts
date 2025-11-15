// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login.component';
import { HomeComponent } from './pages/home/home.component';

// --- IMPORTA AMBOS GUARDIANES ---
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  // --- Rutas Públicas ---
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '', // Redirige a /home (ruta protegida)
    redirectTo: '/home',
    pathMatch: 'full',
  },

  // --- Rutas Protegidas (Requieren solo Autenticación) ---
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard],
  },

  // --- Rutas Protegidas por Rol (Basado en GET de tu backend) ---

  {
    // CORRECCIÓN: Ruta en minúscula
    path: 'citas',
    loadComponent: () => import('./pages/citas/citas.component').then((m) => m.CitasComponent),
    canActivate: [
      authGuard,
      // CORRECTO: Todos los roles pueden ver sus citas
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']),
    ],
  },
  {
    // CORRECCIÓN: Ruta en minúscula y nombre 'formulas'
    path: 'formulas',
    loadComponent: () =>
      import('./features/formulas/pages/formula-list.component').then(
        (m) => m.FormulaListComponent
      ),
    canActivate: [
      authGuard,
      // CORRECCIÓN: Arreglado el tipo 'ADMINISTRADOR'
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']),
    ],
  },
  {
    // CORRECCIÓN: Ruta en minúscula
    path: 'eps',
    loadComponent: () =>
      import('./features/eps/pages/eps/eps').then((m) => m.EpsComponent),
    canActivate: [
      authGuard,
      // CORRECCIÓN: Tu backend (GET /eps) permite a todos ver la lista
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']),
    ],
  },
  {
    // CORRECCIÓN: Ruta en minúscula
    path: 'especialidades',
    loadComponent: () =>
      import('./features/especialidades/pages/especialidades/especialidades').then(
        (m) => m.EspecialidadesComponent
      ),
    canActivate: [
      authGuard,
      // CORRECCIÓN: Tu backend (GET /especialidades) permite a todos ver la lista
      roleGuard(['ADMINISTRADOR', 'MEDICO', 'PACIENTE']),
    ],
  },
  {
    // CORRECCIÓN: Ruta en minúscula
    path: 'medicamentos',
    loadComponent: () =>
      import('./features/medicamentos/pages/medicamentos/medicamentos').then(
        (m) => m.Medicamentos
      ),
    canActivate: [
      authGuard,
      // CORRECCIÓN: Tu backend (GET /medicamentos) permite a ADMIN y MEDICO
      roleGuard(['ADMINISTRADOR', 'MEDICO']),
    ],
  },

  // Ruta comodín
  { path: '**', redirectTo: 'home' }, // Mejor redirigir a home si ya está logueado
];
