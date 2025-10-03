package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.services.MedicamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbMedicamentoService")
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public List<Medicamento> getMedicamentos() {
        return medicamentoRepository.findAll();
    }

    @Override
    public Medicamento getMedicamentoById(Integer idMedicamento) {
        return medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException("Medicamento con id " + idMedicamento + " no encontrado"));
    }

    @Override
    public Medicamento postMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento putMedicamento(Integer idMedicamento, Medicamento medicamento) {
        return medicamentoRepository.findById(idMedicamento)
                .map(m -> {
                    m.setNombreMedicamento(medicamento.getNombreMedicamento());
                    m.setPrecio(medicamento.getPrecio());
                    return medicamentoRepository.save(m);
                })
                .orElseThrow(() -> new MedicamentoNoEncontradoException("Medicamento con id " + idMedicamento + " no encontrado"));
    }

    @Override
    public void deleteMedicamento(Integer idMedicamento) {
        Medicamento medicamento = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException("Medicamento con id " + idMedicamento + " no encontrado"));
        medicamentoRepository.delete(medicamento);
    }

    @Override
    public Medicamento patchMedicamento(Integer idMedicamento, Medicamento medicamento) {
        Medicamento existente = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException("Medicamento con id " + idMedicamento + " no encontrado"));

        if (medicamento.getNombreMedicamento() != null) {
            existente.setNombreMedicamento(medicamento.getNombreMedicamento());
        }
        if (medicamento.getPrecio() != null) {
            existente.setPrecio(medicamento.getPrecio());
        }

        return medicamentoRepository.save(existente);
    }
}
