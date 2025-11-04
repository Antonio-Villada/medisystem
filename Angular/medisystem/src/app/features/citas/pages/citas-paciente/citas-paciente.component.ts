// src/app/features/citas/pages/citas-paciente/citas-paciente.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

// --- IMPORTS DE SERVICIOS Y MODELOS ---
import { AuthService } from '../../../../core/services/auth.service';
import { CitasService } from '../../../../features/citas/services/citas.service';
import { CitaResponse, CitaRequest } from '../../../../features/citas/models/cita.models';
import { Medico } from '../../../../features/medicos/models/medico.interface';
import { MedicoService } from '../../../../features/medicos/services/medico.service';
import { PacientePerfil } from '../../../../features/pacientes/services/paciente.service';
import { PacienteService } from '../../../../features/pacientes/services/paciente.service';

@Component({
  selector: 'app-citas-paciente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './citas-paciente.component.html',
  styleUrls: ['./citas-paciente.component.css'],
})
export class CitasPacienteComponent implements OnInit {
  public listaDeCitas: CitaResponse[] = [];
  public listaDeMedicos: Medico[] = [];
  public idPacienteAutenticado: string | null = null;

  public isLoading = true;
  public errorMensaje: string | null = null;

  public citaForm!: FormGroup;
  public formSubmitted = false;
  public successMessage: string | null = null;
  public formError: string | null = null;

  // --- NUEVA VARIABLE PARA EL FLUJO DEL MODAL ---
  public idCitaACancelar: number | null = null;

  constructor(
    private authService: AuthService,
    private citasService: CitasService,
    private pacienteService: PacienteService,
    private medicoService: MedicoService,
    private fb: FormBuilder
  ) {
    this.crearFormulario();
  }

  private crearFormulario(): void {
    this.citaForm = this.fb.group({
      idMedico: ['', [Validators.required]],
      horaInicio: ['', [Validators.required]],
      fecha: ['', [Validators.required]],
      observaciones: [''],
    });
  }

  ngOnInit(): void {
    this.cargarCitas();
    this.cargarMedicos();
    this.cargarIdPaciente();
  }

  // ==========================================================
  // LÓGICA DE AGENDAMIENTO (Funcional)
  // ==========================================================
  public agendarCita(): void {
    this.formSubmitted = true;
    this.formError = null;
    this.successMessage = null;

    if (this.citaForm.invalid || !this.idPacienteAutenticado) {
      this.formError = this.idPacienteAutenticado
        ? 'Por favor, complete todos los campos requeridos.'
        : 'Error: No se pudo verificar su identidad (cédula).';
      return;
    }

    const formValue = this.citaForm.value;

    const citaRequest: CitaRequest = {
      fecha: formValue.fecha,
      horaInicio: formValue.horaInicio,
      observaciones: formValue.observaciones,
      idMedico: Number(formValue.idMedico),
      idPaciente: this.idPacienteAutenticado,
    };

    this.citasService.agendarCita(citaRequest).subscribe({
      next: (cita) => {
        this.successMessage = `Cita agendada con éxito para el ${cita.fecha} a las ${cita.horaInicio}.`;
        this.formSubmitted = false;
        this.citaForm.reset();
        this.cargarCitas();
      },
      error: (err) => {
        console.error('Error al agendar cita:', err.error);
        this.formError =
          err.error.message || 'Error al agendar la cita. Verifique el horario y disponibilidad.';
        this.formSubmitted = false;
      },
    });
  }

  // ==========================================================
  // LÓGICA DE CANCELACIÓN (IMPLEMENTADA)
  // ==========================================================

  // 🌟 1. Método llamado por el botón: guarda el ID y abre el modal
  public iniciarCancelacionVisual(idCita: number): void {
    this.idCitaACancelar = idCita;
    this.errorMensaje = null;
    this.successMessage = null;
    this.formError = null;
  }

  // 🌟 2. Método llamado por el botón 'Sí, Cancelar Ahora' del modal
  public ejecutarCancelacionConfirmada(): void {
    if (this.idCitaACancelar !== null) {
      this.cancelarCita(this.idCitaACancelar);
      this.idCitaACancelar = null; // Limpiar la variable temporal
    }
  }

  // 3. Lógica que llama al backend (DELETE)
  private cancelarCita(idCita: number): void {
    this.errorMensaje = null;
    this.successMessage = null;

    this.citasService.deleteCita(idCita).subscribe({
      next: () => {
        this.successMessage = `La cita con ID ${idCita} ha sido cancelada exitosamente.`;
        // Elimina la cita de la lista sin recargar
        this.listaDeCitas = this.listaDeCitas.filter((c) => c.idCita !== idCita);
      },
      error: (err) => {
        console.error('Error al cancelar cita:', err.error);
        // Si el backend devuelve un mensaje de error (ej: anticipación), lo mostramos.
        this.formError =
          err.error.message ||
          `Error al cancelar la cita ${idCita}. Verifique la anticipación requerida.`;
      },
    });
  }

  // Función de ayuda para fechas
  public isFutureDate(fecha: string): boolean {
    const citaDate = new Date(fecha);
    const today = new Date();
    return citaDate.setHours(0, 0, 0, 0) >= today.setHours(0, 0, 0, 0);
  }

  // ==========================================================
  // MÉTODOS DE CARGA (Funcionales)
  // ==========================================================
  private cargarCitas(): void {
    this.isLoading = true;
    this.errorMensaje = null;

    this.citasService.getAllCitas().subscribe({
      next: (data) => {
        this.listaDeCitas = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error al cargar citas:', err);
        this.errorMensaje = 'No se pudieron cargar sus citas. Intente más tarde.';
        this.isLoading = false;
      },
    });
  }

  private cargarMedicos(): void {
    this.medicoService.getMedicosParaUI().subscribe({
      next: (data) => {
        this.listaDeMedicos = data;
      },
      error: (err) => {
        console.error('Error al cargar médicos:', err);
      },
    });
  }

  private cargarIdPaciente(): void {
    this.pacienteService.getMiPerfil().subscribe({
      next: (paciente) => {
        this.idPacienteAutenticado = paciente.idPaciente;
      },
      error: (err) => {
        console.error('Error al cargar perfil del paciente:', err);
        this.errorMensaje =
          'Error crítico: No se pudo obtener su ID de paciente. No podrá agendar citas.';
      },
    });
  }
}
