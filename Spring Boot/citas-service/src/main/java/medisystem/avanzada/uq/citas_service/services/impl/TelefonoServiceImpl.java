package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
import medisystem.avanzada.uq.citas_service.services.TelefonoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbTelefonoService")
public class TelefonoServiceImpl implements TelefonoService {

    private final TelefonoRepository telefonoRepository;

    public TelefonoServiceImpl(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }

    @Override
    public List<Telefono> getTelefonos() {
        return telefonoRepository.findAll();
    }

    @Override
    public Telefono getTelefonoById(Integer idTelefono) {
        return telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(
                        "Teléfono con id " + idTelefono + " no encontrado"
                ));
    }

    @Override
    public Telefono postTelefono(Telefono telefono) {
        return telefonoRepository.save(telefono);
    }

    @Override
    public Telefono putTelefono(Integer idTelefono, Telefono telefono) {
        return telefonoRepository.findById(idTelefono)
                .map(t -> {
                    t.setTelefono(telefono.getTelefono());
                    return telefonoRepository.save(t);
                })
                .orElseThrow(() -> new TelefonoNoEncontradoException(
                        "Teléfono con id " + idTelefono + " no encontrado"
                ));
    }

    @Override
    public void deleteTelefono(Integer idTelefono) {
        Telefono telefono = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(
                        "Teléfono con id " + idTelefono + " no encontrado"
                ));
        telefonoRepository.delete(telefono);
    }

    @Override
    public Telefono patchTelefono(Integer idTelefono, Telefono telefono) {
        Telefono existente = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(
                        "Teléfono con id " + idTelefono + " no encontrado"
                ));
        if (telefono.getTelefono() != null) {
            existente.setTelefono(telefono.getTelefono());
        }
        return telefonoRepository.save(existente);
    }
}
