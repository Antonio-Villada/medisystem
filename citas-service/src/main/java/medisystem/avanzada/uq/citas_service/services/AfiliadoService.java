package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Afiliado;
import medisystem.avanzada.uq.citas_service.repositories.AfiliadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AfiliadoService {

    private final AfiliadoRepository afiliadoRepository;

    public AfiliadoService(AfiliadoRepository afiliadoRepository) {
        this.afiliadoRepository = afiliadoRepository;
    }

    public List<Afiliado> listarTodos() {
        return afiliadoRepository.findAll();
    }

    public Optional<Afiliado> buscarPorId(Long id) {
        return afiliadoRepository.findById(id);
    }

    public Afiliado guardar(Afiliado afiliado) {
        return afiliadoRepository.save(afiliado);
    }

    public void eliminar(Long id) {
        afiliadoRepository.deleteById(id);
    }
}
