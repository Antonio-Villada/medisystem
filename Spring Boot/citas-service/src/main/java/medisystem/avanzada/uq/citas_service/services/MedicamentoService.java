package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import java.util.List;

public interface MedicamentoService {

    List<MedicamentoResponseDTO> getMedicamentos();

    // CAMBIADO: Integer -> Long
    MedicamentoResponseDTO getMedicamentoById(Long idMedicamento);

    MedicamentoResponseDTO postMedicamento(MedicamentoRequestDTO medicamentoDTO);

    // CAMBIADO: Integer -> Long
    MedicamentoResponseDTO putMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO);

    // CAMBIADO: Integer -> Long
    void deleteMedicamento(Long idMedicamento);

    // CAMBIADO: Integer -> Long
    MedicamentoResponseDTO patchMedicamento(Long idMedicamento, MedicamentoRequestDTO medicamentoDTO);
}