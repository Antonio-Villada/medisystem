package medisystem.avanzada.uq.citas_service.dtos.cita;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {

    private Long idCita;


    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String observaciones;
    private MedicoResponseDTO medico;
    private PacienteResponseDTO paciente;
    private FormulaResponseDTO formula;

}