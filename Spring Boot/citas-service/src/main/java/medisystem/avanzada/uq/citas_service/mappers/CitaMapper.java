package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class CitaMapper {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public Cita toEntity(CitaRequestDTO dto, Medico medico, Paciente paciente) {
        Cita cita = new Cita();
        cita.setFecha(LocalDate.parse(dto.getFecha(), DATE_FORMAT));
        cita.setHoraInicio(LocalTime.parse(dto.getHoraInicio(), TIME_FORMAT));
        cita.setHoraFin(cita.getHoraInicio().plusHours(1));
        cita.setObservaciones(dto.getObservaciones());
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        return cita;
    }

    public CitaResponseDTO toResponseDTO(Cita cita,
                                         MedicoResponseDTO medicoDTO,
                                         PacienteResponseDTO pacienteDTO) {

        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setFecha(cita.getFecha().format(DATE_FORMAT));
        dto.setHoraInicio(cita.getHoraInicio().format(TIME_FORMAT));
        dto.setHoraFin(cita.getHoraFin().format(TIME_FORMAT));
        dto.setObservaciones(cita.getObservaciones());
        dto.setMedico(medicoDTO);
        dto.setPaciente(pacienteDTO);
        return dto;
    }
}
