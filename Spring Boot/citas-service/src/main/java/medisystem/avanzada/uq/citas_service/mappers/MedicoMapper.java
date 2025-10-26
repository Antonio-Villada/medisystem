package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.Rol; // Necesario para el método de ayuda

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MedicoMapper {


    // DTO a ENTITY (Creación/Actualización)

    /**
     * Mapea MedicoRequestDTO a Medico.
     * El servicio es responsable de obtener/crear 'usuario' y 'especialidad' y pasarlos como argumentos.
     */
    @Mapping(target = "especialidad", source = "especialidad") // Mapea el objeto Especialidad
    @Mapping(target = "usuario", source = "usuario")           // Mapea el objeto Usuario
    @Mapping(target = "idMedico", ignore = true)               // El ID es autogenerado por la BD (ignoramos el source del DTO)
    Medico toEntity(MedicoRequestDTO dto, Usuario usuario, Especialidad especialidad);


    // ENTITY a RESPONSE DTO (Lectura)
    /**
     * Mapea la Entidad Medico a MedicoResponseDTO.
     */
    @Mapping(target = "nombreEspecialidad", source = "medico.especialidad.nombreEspecialidad") // Obtiene el nombre
    @Mapping(target = "username", source = "medico.usuario.username")                       // Obtiene el username
    @Mapping(target = "roles", source = "medico.usuario.roles", qualifiedByName = "rolesToStringSet") // Usa el método de ayuda
    MedicoResponseDTO toResponseDTO(Medico medico);


    //  Método de Ayuda para Mapeo Complejo (Set<Rol> a Set<String>)

    /**
     * Convierte el Set<Rol> del Usuario en un Set<String> con los nombres de los roles.
     * Este método reemplaza la lógica manual que tenías para extraer el rol.
     */
    @Named("rolesToStringSet")
    default Set<String> rolesToStringSet(Set<Rol> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(rol -> rol.getNombre().name()) // Convierte el Enum RolNombre a su String
                .collect(Collectors.toSet());
    }
}