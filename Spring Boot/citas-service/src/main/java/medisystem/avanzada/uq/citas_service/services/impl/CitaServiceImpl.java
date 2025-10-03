package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.services.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbCitaService")
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public List<Cita> getCitas() {
        return citaRepository.findAll();
    }

    @Override
    public Cita getCitaById(Integer idCita) {
        return citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
    }

    @Override
    public Cita postCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public Cita putCita(Integer idCita, Cita cita) {
        return citaRepository.findById(idCita)
                .map(c -> {
                    c.setFecha(cita.getFecha());
                    c.setIdMedico(cita.getIdMedico());
                    c.setIdPaciente(cita.getIdPaciente());
                    c.setObservaciones(cita.getObservaciones());
                    c.setIdFormula(cita.getIdFormula());
                    return citaRepository.save(c);
                })
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
    }

    @Override
    public void deleteCita(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));
        citaRepository.delete(cita);
    }

    @Override
    public Cita patchCita(Integer idCita, Cita cita) {
        Cita existente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));

        if (cita.getFecha() != null) {
            existente.setFecha(cita.getFecha());
        }
        if (cita.getIdMedico() != null) {
            existente.setIdMedico(cita.getIdMedico());
        }
        if (cita.getIdPaciente() != null) {
            existente.setIdPaciente(cita.getIdPaciente());
        }
        if (cita.getObservaciones() != null) {
            existente.setObservaciones(cita.getObservaciones());
        }
        if (cita.getIdFormula() != null) {
            existente.setIdFormula(cita.getIdFormula());
        }

        return citaRepository.save(existente);
    }
}
