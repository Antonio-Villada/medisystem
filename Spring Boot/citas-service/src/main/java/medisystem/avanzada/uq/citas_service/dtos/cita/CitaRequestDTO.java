package medisystem.avanzada.uq.citas_service.dtos.cita;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaRequestDTO {

    @NotNull(message = "La fecha no puede ser nula.")
    private LocalDate fecha;       // CAMBIADO: String -> LocalDate

    @NotNull(message = "La hora de inicio no puede ser nula.")
    private LocalTime horaInicio;  // CAMBIADO: String -> LocalTime

    @Size(max = 500, message = "Las observaciones no deben exceder los 500 caracteres.")
    private String observaciones;

    @NotNull(message = "El ID del médico es obligatorio.")
    private Long idMedico;

    @NotBlank(message = "El ID del paciente es obligatorio.")
    private String idPaciente;


}