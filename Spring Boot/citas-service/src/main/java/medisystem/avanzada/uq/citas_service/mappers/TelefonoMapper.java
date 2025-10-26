package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Telefono;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TelefonoMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea TelefonoRequestDTO a Telefono.
     * El campo 'telefono' se mapea automáticamente.
     * Ignoramos el ID ya que es autogenerado.
     */
    @Mapping(target = "idTelefono", ignore = true)
    Telefono toEntity(TelefonoRequestDTO dto);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Telefono a TelefonoResponseDTO.
     * Ambos campos tienen el mismo nombre y se mapean automáticamente.
     */
    TelefonoResponseDTO toResponseDTO(Telefono entity);
}