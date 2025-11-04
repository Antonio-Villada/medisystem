// src/app/pages/citas/citas.component.ts

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

// Importar el servicio de autenticación
import { AuthService } from '../../core/services/auth.service';

// --- IMPORTAR LOS COMPONENTES HIJOS ---
// Debes asegurarte que las rutas relativas sean correctas
import { CitasPacienteComponent } from '../../features/citas/pages/citas-paciente/citas-paciente.component';
// Esqueletos
import { CitasMedicoComponent } from '../../features/citas/pages/citas-medico/citas-medico.component';
import { CitasAdminComponent } from '../../features/citas/pages/citas-admin/citas-admin.component';

@Component({
  selector: 'app-citas',
  standalone: true,
  // --- AÑADIR LOS COMPONENTES HIJOS A LOS imports ---
  imports: [CommonModule, CitasPacienteComponent, CitasMedicoComponent, CitasAdminComponent],
  templateUrl: './citas.component.html',
  styleUrls: ['./citas.component.css'],
})
export class CitasComponent {
  // Variables para los roles
  public esAdmin: boolean;
  public esMedico: boolean;
  public esPaciente: boolean;

  constructor(private authService: AuthService) {
    // La única lógica es verificar el rol en el constructor
    this.esAdmin = this.authService.tieneRol('ADMINISTRADOR');
    this.esMedico = this.authService.tieneRol('MEDICO');
    this.esPaciente = this.authService.tieneRol('PACIENTE');
  }
}

// Nota: Toda la lógica de carga, formularios y servicios ha sido movida.
