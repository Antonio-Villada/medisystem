// Basado en MedicamentoResponseDTO
export interface Medicamento {
  idMedicamento: number;
  nombreMedicamento: string;
  precio: number;
}

// Basado en DetalleFormulaResponseDTO
export interface DetalleFormula {
  idDetalleFormula: number;
  cantidad: number;
  dosis: string;
  medicamento: Medicamento;
}

// Basado en FormulaResponseDTO
export interface Formula {
  idFormula: number;
  fecha: string; // Angular manejará el string de LocalDate
  idCita: number;
  detalles: DetalleFormula[];
}

// --- Modelos de Cita (necesarios para el rol PACIENTE) ---

// Basado en PacienteResponseDTO
export interface Paciente {
  idPaciente: string;
  nombrePaciente: string;
}

// Basado en MedicoResponseDTO
export interface Medico {
  idMedico: number;
  nombreMedico: string;
}

// Basado en CitaResponseDTO
export interface Cita {
  idCita: number;
  fecha: string;
  horaInicio: string;
  medico: Medico;
  paciente: Paciente;
  formula: Formula | null; // La fórmula puede ser nula
}
