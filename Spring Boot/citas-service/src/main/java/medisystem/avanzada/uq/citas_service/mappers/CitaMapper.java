package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



// Indicamos que use otros mappers para anidar los objetos DTO
@Mapper(componentModel = "spring", uses = {MedicoMapper.class, PacienteMapper.class, FormulaMapper.class})
public interface CitaMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea CitaRequestDTO a Cita.
     *
     * MapStruct se encarga automáticamente de:
     * - fecha (LocalDate a LocalDate)
     * - horaInicio (LocalTime a LocalTime)
     * - observaciones
     *
     * El servicio pasa las entidades Medico y Paciente.
     */
    @Mapping(target = "medico", source = "medico")
    @Mapping(target = "paciente", source = "paciente")

    // horaFin se calcula con el ciclo de vida (@PrePersist) en la entidad Cita.
    // Lo ignoramos para evitar que MapStruct intente mapear un valor nulo.
    @Mapping(target = "horaFin", ignore = true)

    @Mapping(target = "idCita", ignore = true) // El ID es autogenerado.
    @Mapping(target = "formula", ignore = true) // La fórmula se crea después, no en el request
    Cita toEntity(CitaRequestDTO dto, Medico medico, Paciente paciente);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Cita a CitaResponseDTO.
     *
     * MapStruct usa los mappers definidos en 'uses' para anidar:
     * - medico (llama a MedicoMapper.toResponseDTO)
     * - paciente (llama a PacienteMapper.toResponseDTO)
     * - formula (llama a FormulaMapper.toResponseDTO)
     */

    // Los DTOs ya están correctos (Long, LocalDate, LocalTime), por lo que mapean directamente.
    @Mapping(target = "medico", source = "cita.medico")
    @Mapping(target = "paciente", source = "cita.paciente")
    @Mapping(target = "formula", source = "cita.formula") // Usa el FormulaMapper
    CitaResponseDTO toResponseDTO(Cita cita);
}