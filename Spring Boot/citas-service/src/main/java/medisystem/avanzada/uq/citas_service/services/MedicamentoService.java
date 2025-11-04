package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import java.util.List;

public interface MedicamentoService {

    List<MedicamentoResponseDTO> getMedicamentos();

    MedicamentoResponseDTO getMedicamentoById(Long idMedicamento);
    MedicamentoResponseDTO postMedicamento(MedicamentoRequestDTO medicamentoDTO);
    MedicamentoResponseDTO putMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO);
    void deleteMedicamento(Long idMedicamento);
    MedicamentoResponseDTO patchMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO);
}