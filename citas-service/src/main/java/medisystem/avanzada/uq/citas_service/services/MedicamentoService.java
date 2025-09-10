package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicamentoService {

    private MedicamentoRepository medicamentoRepository;


    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento crearMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    //Metodo que retorna un medicamento buscado po id
    public Optional<Medicamento> buscarMedicamentoPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    //Metodo que elimina medicamento por id
    public void eliminarMedicamentoId(Long id) {
        medicamentoRepository.deleteById(id);
    }

    public void eliminarMedicamento(Long id) {
        if (medicamentoRepository.existsById(id)) {
            medicamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Medicamento con id " + id + " no encontrado");
        }
    }

}
