package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Formula;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Asegúrate de que este mapper conoce los mappers que necesitará
@Mapper(componentModel = "spring", uses = {DetalleFormulaMapper.class})
public interface FormulaMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea FormulaRequestDTO a Formula.
     */
    @Mapping(target = "cita", source = "cita")
    @Mapping(target = "idFormula", ignore = true) // El ID es autogenerado
    // La lista de detalles se ignoran y se manejan en el servicio después de la creación
    @Mapping(target = "detalles", ignore = true)

    // CORRECCIÓN: Resuelve la ambigüedad indicando que 'fecha' viene del 'dto'
    @Mapping(target = "fecha", source = "dto.fecha")
    Formula toEntity(FormulaRequestDTO dto, Cita cita);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad Formula a FormulaResponseDTO.
     *
     * MapStruct usa el DetalleFormulaMapper (definido en 'uses') para la lista 'detalles'.
     */

    // Mapeo del idCita (aplanamos la relación inversa para evitar el bucle)
    @Mapping(target = "idCita", source = "formula.cita.idCita")

    // Mapeo de la lista de detalles (ahora que la propiedad 'detalles' existe en la Entidad Formula)
    @Mapping(target = "detalles", source = "formula.detalles")

    // El mapeo de 'fecha' y 'idFormula' es implícito.
    FormulaResponseDTO toResponseDTO(Formula formula);
}