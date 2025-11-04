// src/app/features/citas/models/cita.models.ts

// --- Basado en tu MedicoResponseDTO ---
export interface Medico {
  idMedico: number;
  nombreMedico: string;
  telefono: string;
  correo: string;
  nombreEspecialidad: string;
  username: string;
}

// --- Basado en tu PacienteResponseDTO ---
export interface Paciente {
  idPaciente: string; // La cédula
  nombrePaciente: string;
  ciudad: string;
  correo: string;
  nombreEps: string;
  username: string;
}

// --- Basado en tu CitaResponseDTO ---
export interface CitaResponse {
  idCita: number;
  fecha: string; // Las fechas se manejan como string en JSON
  horaInicio: string; // Las horas se manejan como string
  horaFin: string;
  observaciones: string;
  medico: Medico;
  paciente: Paciente;
  // formula: any; // Omitido por ahora para simplicidad
}

// --- Basado en tu CitaRequestDTO ---
export interface CitaRequest {
  fecha: string;
  horaInicio: string;
  observaciones: string;
  idMedico: number;
  idPaciente: string; // La cédula
}
