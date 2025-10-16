package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;
import medisystem.avanzada.uq.citas_service.service.PacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("dbPacienteService")
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public List<Paciente> getPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente getPacienteById(String idPaciente) {
        return pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
    }

    @Override
    public Paciente postPaciente(Paciente paciente) {
        // Si el paciente no tiene ID, se genera uno automáticamente
        if (paciente.getIdPaciente() == null || paciente.getIdPaciente().isBlank()) {
            paciente.setIdPaciente(UUID.randomUUID().toString());
        }

        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente putPaciente(String idPaciente, Paciente paciente) {
        return pacienteRepository.findById(idPaciente)
                .map(p -> {
                    p.setNombrePaciente(paciente.getNombrePaciente());
                    p.setCiudad(paciente.getCiudad());
                    p.setCorreo(paciente.getCorreo());
                    p.setEps(paciente.getEps());
                    return pacienteRepository.save(p);
                })
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
    }

    @Override
    public void deletePaciente(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
        pacienteRepository.delete(paciente);
    }

    @Override
    public Paciente patchPaciente(String idPaciente, Paciente paciente) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));

        if (paciente.getNombrePaciente() != null) {
            existente.setNombrePaciente(paciente.getNombrePaciente());
        }
        if (paciente.getCiudad() != null) {
            existente.setCiudad(paciente.getCiudad());
        }
        if (paciente.getCorreo() != null) {
            existente.setCorreo(paciente.getCorreo());
        }
        if (paciente.getEps() != null) {
            existente.setEps(paciente.getEps());
        }

        return pacienteRepository.save(existente);
    }
}
