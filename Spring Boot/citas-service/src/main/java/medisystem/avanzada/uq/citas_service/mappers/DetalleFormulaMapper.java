package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.detalleFormula.DetalleFormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.DetalleFormula;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Indicamos que use el MedicamentoMapper para mapear el objeto Medicamento
@Mapper(componentModel = "spring", uses = {MedicamentoMapper.class})
public interface DetalleFormulaMapper {

    // --------------------------------------------------------------------
    // 1. DTO a ENTITY (Creación)
    // --------------------------------------------------------------------

    /**
     * Mapea DetalleFormulaRequestDTO a DetalleFormula.
     */
    @Mapping(target = "formula", source = "formula")
    @Mapping(target = "medicamento", source = "medicamento")
    @Mapping(target = "idDetalleFormula", ignore = true)
    DetalleFormula toEntity(DetalleFormulaRequestDTO dto, Formula formula, Medicamento medicamento);


    // --------------------------------------------------------------------
    // 2. ENTITY a RESPONSE DTO (Lectura)
    // --------------------------------------------------------------------

    /**
     * Mapea la Entidad DetalleFormula a DetalleFormulaResponseDTO.
     *
     * Se elimina el mapeo/ignorancia de 'formula' ya que el campo no existe en el DTO.
     * Los mapeos idDetalleFormula, cantidad y dosis son implícitos.
     */

    // Ya que DetalleFormulaResponseDTO no tiene un campo 'formula', no es necesario ignorarlo.
    // Solo mapeamos el medicamento
    @Mapping(target = "medicamento", source = "entity.medicamento")

    // Si quisieras el ID de la fórmula, lo agregarías al DTO y lo mapearías así:
    // @Mapping(target = "idFormula", source = "entity.formula.idFormula")

    DetalleFormulaResponseDTO toResponseDTO(DetalleFormula entity);
}