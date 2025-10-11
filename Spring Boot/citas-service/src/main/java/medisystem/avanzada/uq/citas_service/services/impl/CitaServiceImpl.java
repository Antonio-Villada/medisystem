package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.services.CitaService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;


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
    public Cita putCita(Integer idCita, Cita cita) {
        //Buscar la cita existente
        Cita citaExistente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));

        //Validar conflicto de horario (el médico no puede tener dos citas a la misma hora)
        boolean conflicto = citaRepository.existsByMedicoAndFechaAndHoraInicio(
                cita.getMedico(),
                cita.getFecha(),
                cita.getHoraInicio()
        );

        if (conflicto && !citaExistente.getIdCita().equals(cita.getIdCita())) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en ese horario.");
        }

        //Actualizar los campos permitidos
        citaExistente.setFecha(cita.getFecha());
        citaExistente.setHoraInicio(cita.getHoraInicio());
        citaExistente.setHoraFin(cita.getHoraInicio().plusHours(1));
        citaExistente.setMedico(cita.getMedico());
        citaExistente.setPaciente(cita.getPaciente());
        citaExistente.setObservaciones(cita.getObservaciones());
        citaExistente.setFormula(cita.getFormula());

        //Guardar y retornar la cita actualizada
        return citaRepository.save(citaExistente);
    }


    @Override
    public void deleteCita(Integer idCita) {
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));

        if (!cita.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Solo puede cancelar citas con al menos un día de anticipación.");
        }

        citaRepository.delete(cita);
    }



    @Override
    public Cita patchCita(Integer idCita, Cita cita) {
        Cita existente = citaRepository.findById(idCita)
                .orElseThrow(() -> new CitaNoEncontradaException("Cita con id " + idCita + " no encontrada"));

        if (cita.getFecha() != null) {
            existente.setFecha(cita.getFecha());
        }
        if (cita.getMedico() != null) {
            existente.setMedico(cita.getMedico());
        }
        if (cita.getPaciente() != null) {
            existente.setPaciente(cita.getPaciente());
        }
        if (cita.getObservaciones() != null) {
            existente.setObservaciones(cita.getObservaciones());
        }
        if (cita.getFormula() != null) {
            existente.setFormula(cita.getFormula());
        }

        return citaRepository.save(existente);
    }


    @Override
    public Cita postCita(Cita cita) {

        //  Validar horario permitido (8:00 a.m. - 6:00 p.m.)
        if (cita.getHoraInicio() == null) {
            throw new IllegalArgumentException("Debe especificar la hora de inicio de la cita.");
        }

        LocalTime horaInicio = cita.getHoraInicio();
        LocalTime horaFin = cita.getHoraFin() != null ? cita.getHoraFin() : horaInicio.plusHours(1);

        if (horaInicio.isBefore(LocalTime.of(8, 0)) || horaFin.isAfter(LocalTime.of(18, 0))) {
            throw new IllegalArgumentException("Las citas solo pueden programarse entre 8:00 a.m. y 6:00 p.m.");
        }

        //Validar duración aproximada de una hora
        if (!horaFin.equals(horaInicio.plusHours(1))) {
            throw new IllegalArgumentException("Cada cita debe tener una duración de una hora.");
        }

        //Validar fecha permitida (hoy y los próximos 6 días)
        LocalDate hoy = LocalDate.now();
        if (cita.getFecha().isBefore(hoy) || cita.getFecha().isAfter(hoy.plusDays(6))) {
            throw new IllegalArgumentException("Solo se pueden agendar citas para los próximos 6 días.");
        }

        // Validar máximo 10 citas por médico al día
        int citasMedicoDia = citaRepository.countByMedicoAndFecha(cita.getMedico(), cita.getFecha());
        if (citasMedicoDia >= 10) {
            throw new IllegalArgumentException("El médico ya tiene el máximo de 10 citas para este día.");
        }

        // Validar que no exista otra cita del mismo médico en la misma fecha y hora
        boolean ocupado = citaRepository.existsByMedicoAndFechaAndHoraInicio(
                cita.getMedico(),
                cita.getFecha(),
                cita.getHoraInicio()
        );

        if (ocupado) {
            throw new IllegalArgumentException("El médico ya tiene una cita programada en ese horario.");
        }

        // si todo esta bien garda la cita
        cita.setHoraFin(horaFin);
        return citaRepository.save(cita);
    }
}