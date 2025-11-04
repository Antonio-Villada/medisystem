package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.usuario.UsuarioResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.entities.Rol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "roles", source = "usuario.roles", qualifiedByName = "rolesToStringSet")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);


    @Named("rolesToStringSet")
    default Set<String> rolesToStringSet(Set<Rol> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(rol -> rol.getNombre().name())
                .collect(Collectors.toSet());
    }
}