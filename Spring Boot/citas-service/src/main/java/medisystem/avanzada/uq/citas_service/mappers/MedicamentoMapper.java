package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Indicamos que es un componente de Spring y que use el model 'spring'
@Mapper(componentModel = "spring")
public interface MedicamentoMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea MedicamentoRequestDTO a Medicamento.
     * Los campos 'nombreMedicamento' y 'precio' se mapean automáticamente.
     * Ignoramos el ID ya que es autogenerado.
     */
    @Mapping(target = "idMedicamento", ignore = true)
    Medicamento toEntity(MedicamentoRequestDTO dto);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Medicamento a MedicamentoResponseDTO.
     * Todos los campos tienen el mismo nombre y se mapean automáticamente.
     */
    MedicamentoResponseDTO toResponseDTO(Medicamento entity);
}