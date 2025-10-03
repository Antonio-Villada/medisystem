package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.services.MedicoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("dbMedicoService")
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public List<Medico> getMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico getMedicoById(Long idMedico) {
        return medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException(
                        "Médico con id " + idMedico + " no encontrado en BD"
                ));
    }

    @Override
    public Medico postMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public Medico putMedico(Long idMedico, Medico medico) {
        return medicoRepository.findById(idMedico)
                .map(m -> {
                    m.setEspecialidad(medico.getEspecialidad());
                    m.setNombreMedico(medico.getNombreMedico());
                    m.setCorreo(medico.getCorreo());
                    return medicoRepository.save(m);
                })
                .orElseThrow(() -> new MedicoNoEncontradoException(
                        "Médico con id " + idMedico + " no encontrado"
                ));
    }


    @Override
    public void deleteMedico(Long idMedico) {
        Medico medico = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new MedicoNoEncontradoException
                        ("Medico con ID " + idMedico + " no encontrado"));
        medicoRepository.delete(medico);
    }


    @Override
    public Medico patchMedico(Long idMedico, Medico medico) {
        Medico existente = medicoRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Medico no encontrado con id: " + idMedico));

        // Actualizar solo los campos que recibes en el JSON
        if (medico.getNombreMedico() != null) {
            existente.setNombreMedico(medico.getNombreMedico());
        }
        if (medico.getTelefono() != null) {
            existente.setTelefono(medico.getTelefono());
        }
        if(medico.getCorreo() != null){
            existente.setCorreo(medico.getCorreo());
        }
        if(medico.getEspecialidad() != null){
            existente.setEspecialidad(medico.getEspecialidad());
        }
        return medicoRepository.save(existente);
    }
}