package medisystem.avanzada.uq.citas_service.service;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import java.util.List;

public interface MedicamentoService {
    List<Medicamento> getMedicamentos();
    Medicamento getMedicamentoById(Integer idMedicamento);
    Medicamento postMedicamento(Medicamento medicamento);
    Medicamento putMedicamento(Integer idMedicamento, Medicamento medicamento);
    void deleteMedicamento(Integer idMedicamento);
    Medicamento patchMedicamento(Integer idMedicamento, Medicamento medicamento);
}
