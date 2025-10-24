package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class MedicamentoMapper {

    public Medicamento toEntity(MedicamentoRequestDTO dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNombreMedicamento(dto.getNombreMedicamento());
        medicamento.setPrecio(dto.getPrecio());
        return medicamento;
    }

    public MedicamentoResponseDTO toResponseDTO(Medicamento entity) {
        MedicamentoResponseDTO dto = new MedicamentoResponseDTO();
        dto.setIdMedicamento(entity.getIdMedicamento());
        dto.setNombreMedicamento(entity.getNombreMedicamento());
        dto.setPrecio(entity.getPrecio());
        return dto;
    }
}
