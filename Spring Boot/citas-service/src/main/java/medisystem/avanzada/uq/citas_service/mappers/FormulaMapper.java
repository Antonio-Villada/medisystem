package medisystem.avanzada.uq.citas_service.mappers;

import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Formula;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FormulaMapper {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Formula toEntity(FormulaRequestDTO dto, Cita cita) {
        Formula formula = new Formula();
        formula.setCita(cita);
        if (dto.getFecha() != null) {
            formula.setFecha(LocalDate.parse(dto.getFecha(), DATE_FORMAT));
        } else {
            formula.setFecha(LocalDate.now());
        }
        return formula;
    }

    public FormulaResponseDTO toResponseDTO(Formula formula, CitaResponseDTO citaDTO) {
        FormulaResponseDTO dto = new FormulaResponseDTO();
        dto.setIdFormula(formula.getIdFormula());
        dto.setFecha(formula.getFecha() != null ? formula.getFecha().format(DATE_FORMAT) : null);
        dto.setCita(citaDTO);
        return dto;
    }
}
