package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring", uses = {MedicoMapper.class, PacienteMapper.class, FormulaMapper.class})
public interface CitaMapper {

    @Mapping(target = "medico", source = "medico")
    @Mapping(target = "paciente", source = "paciente")
    @Mapping(target = "horaFin", ignore = true)
    @Mapping(target = "idCita", ignore = true)
    @Mapping(target = "formula", ignore = true)
    Cita toEntity(CitaRequestDTO dto, Medico medico, Paciente paciente);
    @Mapping(target = "medico", source = "cita.medico")
    @Mapping(target = "paciente", source = "cita.paciente")
    @Mapping(target = "formula", source = "cita.formula")
    CitaResponseDTO toResponseDTO(Cita cita);
}