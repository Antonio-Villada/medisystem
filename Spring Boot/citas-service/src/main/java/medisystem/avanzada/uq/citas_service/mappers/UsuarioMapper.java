package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.Rol; // Necesario para el método de ayuda

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

// Indicamos que es un componente de Spring
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // --------------------------------------------------------------------
    // 1. ENTITY a RESPONSE DTO (Lectura de perfil/Login)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Usuario a UsuarioResponseDTO.
     * Mapea automáticamente 'id' y 'username'.
     * Utiliza el método de ayuda para mapear los roles.
     * La contraseña se omite por defecto ya que no existe en el DTO.
     */
    @Mapping(target = "roles", source = "usuario.roles", qualifiedByName = "rolesToStringSet")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);


    // --------------------------------------------------------------------
    // 2. Método de Ayuda para Mapeo Complejo (Set<Rol> a Set<String>)
    // --------------------------------------------------------------------

    /**
     * Convierte el Set<Rol> del Usuario en un Set<String> con los nombres de los roles.
     * Este método es idéntico al que usaríamos en MedicoMapper.
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