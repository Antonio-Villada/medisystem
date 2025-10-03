package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.entities.Especialidad;

import java.util.List;

public interface EspecialidadService {
    List<Especialidad> getEspecialidades();
    Especialidad getEspecialidadByid(Long idEspecialidad);
    Especialidad postEspecialidad (Especialidad especialidad);
    Especialidad putEspecialidad (Long idEspecialidad,Especialidad especialidad);
    void deleteEspecialidad (Long idEspecialidad);
    Especialidad pachEspecialidad (Long idEspecialidad, Especialidad especialidad);


}
