// src/app/features/citas/pages/citas-admin/citas-admin.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

// --- IMPORTS DE SERVICIOS Y MODELOS (Rutas Ajustadas) ---
import { CitasService } from '../../../../features/citas/services/citas.service';
import { CitaResponse } from '../../../../features/citas/models/cita.models';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; // Necesario para filtros/edición

@Component({
  selector: 'app-citas-admin',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './citas-admin.component.html', // Debes crear este HTML y CSS
  styleUrls: ['./citas-admin.component.css'],
})
export class CitasAdminComponent implements OnInit {
  // Variables de datos
  public listaTodasCitas: CitaResponse[] = []; // Todas las citas del sistema

  // Variables de estado
  public isLoading = true;
  public errorMensaje: string | null = null;

  // Formulario para filtros o para el CRUD
  public adminForm!: FormGroup;

  constructor(private citasService: CitasService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.cargarTodasCitas();
    // Podríamos inicializar un formulario de filtros aquí
  }

  // ==========================================================
  // LÓGICA DE CARGA DE CITAS TOTALES
  // ==========================================================
  public cargarTodasCitas(): void {
    this.isLoading = true;
    this.errorMensaje = null;

    // Como somos ADMIN, el backend nos devuelve TODAS las citas.
    this.citasService.getAllCitas().subscribe({
      next: (data) => {
        this.listaTodasCitas = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error al cargar la gestión de citas:', err);
        this.errorMensaje = 'No se pudo cargar el listado completo de citas.';
        this.isLoading = false;
      },
    });
  }

  // ==========================================================
  // ACCIONES DEL ADMINISTRADOR (Esqueletos CRUD)
  // ==========================================================

  public editarCita(cita: CitaResponse): void {
    // Lógica para pre-cargar un modal y llamar a citasService.updateCita
    console.log('Admin: Editando cita:', cita.idCita);
  }

  public eliminarCita(idCita: number): void {
    // Lógica para confirmar y llamar a citasService.deleteCita
    console.log('Admin: Eliminando cita:', idCita);
  }

  public abrirModalCreacion(): void {
    // Lógica para abrir un modal con un formulario vacío (Admin puede agendar cualquier cita)
    console.log('Admin: Abriendo modal de creación de cita');
  }
}
