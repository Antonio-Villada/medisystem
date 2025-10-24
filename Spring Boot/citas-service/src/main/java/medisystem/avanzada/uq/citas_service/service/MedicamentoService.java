package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import java.util.List;

public interface MedicamentoService {

    List<MedicamentoResponseDTO> getMedicamentos();
    MedicamentoResponseDTO getMedicamentoById(Integer idMedicamento);
    MedicamentoResponseDTO postMedicamento(MedicamentoRequestDTO medicamentoDTO);
    MedicamentoResponseDTO putMedicamento(Integer idMedicamento, MedicamentoRequestDTO medicamentoDTO);
    void deleteMedicamento(Integer idMedicamento);
    MedicamentoResponseDTO patchMedicamento(Integer idMedicamento, MedicamentoRequestDTO medicamentoDTO);
}
