package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import medisystem.avanzada.uq.citas_service.services.EspecialidadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbEspecialidadService")
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    public List<Especialidad> getEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Override
    public Especialidad getEspecialidadByid(Long idEspecialidad) {
        return especialidadRepository
                .findById(idEspecialidad)
                .orElseThrow(() -> new EspecialidadNoEncontradaException(
                        "Especialidad con id " + idEspecialidad + "no encontrada en la BD"
                ));
    }

    @Override
    public Especialidad postEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad putEspecialidad(Long idEspecialidad, Especialidad especialidad) {
        return especialidadRepository.findById((idEspecialidad))
                .map( m -> {
                    m.setNombreEspecialidad(especialidad.getNombreEspecialidad());
                    return especialidadRepository.save(m);
                })
                .orElseThrow(()-> new EspecialidadNoEncontradaException
                        ("Especialidad con id" + idEspecialidad + " no encontrada en la Bd"));
    }

    @Override
    public void deleteEspecialidad(Long idEspecialidad) {
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad).orElseThrow(()->new EspecialidadNoEncontradaException("Especialidad por id " + idEspecialidad + "no encontrada en la BD"));
        especialidadRepository.delete(especialidad);
    }

//    @Override
//    public Especialidad pachEspecialidad(Long idEspecialidad, Especialidad especialidad) {
//
//        Especialidad existente = especialidadRepository
//                .findById(idEspecialidad).orElseThrow(()->
//                        new EspecialidadNoEncontradaException
//                                ( "Especialidad por id " + idEspecialidad + "no encontrada" ));
//        if (especialidad.getNombreEspecialidad() != null) {
//            existente.setNombreEspecialidad(especialidad.getNombreEspecialidad());
//        }
//        return especialidadRepository.save(existente);
//    }

    public Especialidad pachEspecialidad(Long id, Especialidad parcial) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new EspecialidadNoEncontradaException("No se encontró especialidad con id " + id));

        // Solo actualizar si el valor no es null
        if (parcial.getNombreEspecialidad() != null) {
            existente.setNombreEspecialidad(parcial.getNombreEspecialidad());
        }

        return especialidadRepository.save(existente);
    }

}
