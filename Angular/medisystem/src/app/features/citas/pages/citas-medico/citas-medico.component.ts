// src/app/features/citas/pages/citas-medico/citas-medico.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

// --- IMPORTS DE SERVICIOS Y MODELOS (Rutas Ajustadas) ---
import { CitasService } from '../../../../features/citas/services/citas.service';
import { CitaResponse } from '../../../../features/citas/models/cita.models';
// Usaremos MedicoService solo si necesitamos obtener los datos del médico
import { MedicoService } from '../../../../features/medicos/services/medico.service';

@Component({
  selector: 'app-citas-medico',
  standalone: true,
  imports: [CommonModule],
  // Debes crear este HTML y CSS en la misma carpeta
  templateUrl: './citas-medico.component.html',
  styleUrls: ['./citas-medico.component.css'],
})
export class CitasMedicoComponent implements OnInit {
  // Variables de datos
  public agenda: CitaResponse[] = []; // Agenda del médico

  // Variables de estado
  public isLoading = true;
  public errorMensaje: string | null = null;

  // Aquí podríamos almacenar el idMedico si fuera necesario,
  // pero el backend filtra por el token, lo cual es mejor.

  constructor(private citasService: CitasService, private medicoService: MedicoService) {}

  ngOnInit(): void {
    this.cargarAgenda();
  }

  // ==========================================================
  // LÓGICA DE CARGA DE AGENDA
  // ==========================================================
  private cargarAgenda(): void {
    this.isLoading = true;
    this.errorMensaje = null;

    // El backend se encarga de filtrar la lista de citas para mostrar
    // solo las que corresponden al médico autenticado (a través del token).
    this.citasService.getAllCitas().subscribe({
      next: (data) => {
        this.agenda = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error al cargar agenda:', err);
        this.errorMensaje = 'No se pudo cargar su agenda de citas.';
        this.isLoading = false;
      },
    });
  }

  // ==========================================================
  // ACCIONES DEL MÉDICO (Esqueletos)
  // ==========================================================

  public iniciarEdicion(cita: CitaResponse): void {
    // Lógica para abrir un modal con los datos de la cita para edición
    console.log('Iniciando edición de cita:', cita.idCita);
  }

  public iniciarCancelacion(idCita: number): void {
    // Lógica para confirmar y llamar a citasService.deleteCita(idCita)
    console.log('Iniciando cancelación de cita:', idCita);
  }
}
