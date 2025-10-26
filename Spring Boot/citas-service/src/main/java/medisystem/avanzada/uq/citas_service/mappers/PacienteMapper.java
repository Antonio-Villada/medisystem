package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.PacienteTelefono;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.Telefono;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    @Mapping(target = "eps", source = "eps")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "telefonos", ignore = true)
    @Mapping(target = "idPaciente", ignore = true)
    Paciente toEntity(PacienteRequestDTO dto, Eps eps, Usuario usuario);

    @Mapping(target = "nombreEps", source = "paciente.eps.nombreEps")
    @Mapping(target = "username", source = "paciente.usuario.username")
    @Mapping(target = "telefonos", source = "paciente.telefonos", qualifiedByName = "telefonosToStringSet")
    PacienteResponseDTO toResponseDTO(Paciente paciente);

    /**
     * MÉTODO SOBRECARGADO para usar en servicios (POST/PUT/PATCH)
     * donde la lista de teléfonos ya fue procesada en el servicio.
     */
    @Mapping(target = "nombreEps", source = "paciente.eps.nombreEps")
    @Mapping(target = "username", source = "paciente.usuario.username")
    @Mapping(target = "telefonos", source = "telefonos") // Mapea el Set<String> directo
    PacienteResponseDTO toResponseDTO(Paciente paciente, Set<String> telefonos);


    @Named("telefonosToStringSet")
    default Set<String> telefonosToStringSet(List<PacienteTelefono> telefonos) {
        if (telefonos == null || telefonos.isEmpty()) {
            return Set.of();
        }

        // Esta línea es correcta si la entidad PacienteTelefono tiene el campo 'telefono'
        // y este campo es de tipo Telefono, y Telefono tiene un getTelefono().
        return telefonos.stream()
                .map(pt -> pt.getTelefono().getTelefono())
                .collect(Collectors.toSet());
    }
}