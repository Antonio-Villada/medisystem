package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Indicamos que es un componente de Spring y que use el model 'spring'
@Mapper(componentModel = "spring")
public interface MedicamentoMapper {

    @Mapping(target = "idMedicamento", ignore = true)
    Medicamento toEntity(MedicamentoRequestDTO dto);

    MedicamentoResponseDTO toResponseDTO(Medicamento entity);
}