package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteRequestDTO dto, Eps eps, Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(dto.getIdPaciente());
        paciente.setNombrePaciente(dto.getNombrePaciente());
        paciente.setCiudad(dto.getCiudad());
        paciente.setCorreo(dto.getCorreo());
        paciente.setEps(eps);
        paciente.setUsuario(usuario);
        return paciente;
    }

    public PacienteResponseDTO toDTO(Paciente paciente, Set<String> telefonos) {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setIdPaciente(paciente.getIdPaciente());
        dto.setNombrePaciente(paciente.getNombrePaciente());
        dto.setCiudad(paciente.getCiudad());
        dto.setCorreo(paciente.getCorreo());
        dto.setNombreEps(paciente.getEps().getNombreEps());
        dto.setUsername(paciente.getUsuario().getUsername());
        dto.setTelefonos(telefonos);
        return dto;
    }
}
