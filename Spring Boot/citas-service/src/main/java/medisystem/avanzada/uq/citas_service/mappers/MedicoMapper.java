package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public Medico toEntity(MedicoRequestDTO dto, Usuario usuario, Especialidad especialidad) {
        Medico medico = new Medico();
        medico.setNombreMedico(dto.getNombreMedico());
        medico.setTelefono(dto.getTelefono());
        medico.setCorreo(dto.getCorreo());
        medico.setUsuario(usuario);
        medico.setEspecialidad(especialidad);
        return medico;
    }

    public MedicoResponseDTO toResponseDTO(Medico medico) {
        String especialidadNombre = medico.getEspecialidad() != null
                ? medico.getEspecialidad().getNombreEspecialidad()
                : null;

        String username = medico.getUsuario() != null
                ? medico.getUsuario().getUsername()
                : null;

        String rol = (medico.getUsuario() != null && !medico.getUsuario().getRoles().isEmpty())
                ? medico.getUsuario().getRoles().iterator().next().getNombre().name()
                : null;

        return new MedicoResponseDTO(
                medico.getIdMedico(),
                medico.getNombreMedico(),
                medico.getTelefono(),
                medico.getCorreo(),
                especialidadNombre,
                username,
                rol
        );
    }
}
