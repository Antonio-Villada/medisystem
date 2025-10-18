package medisystem.avanzada.uq.citas_service.dto;

import java.time.LocalDate;

public class FormulaDTO {
    private Integer idFormula;
    private Integer idCita;
    private LocalDate fecha;

    public FormulaDTO() {}

    public Integer getIdFormula() { return idFormula; }
    public void setIdFormula(Integer idFormula) { this.idFormula = idFormula; }

    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
