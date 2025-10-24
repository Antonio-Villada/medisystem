package medisystem.avanzada.uq.citas_service.dtos.formula;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;

public class FormulaResponseDTO {

    private Integer idFormula;
    private String fecha; // formato: "yyyy-MM-dd"
    private CitaResponseDTO cita;

    public FormulaResponseDTO() {}

    public Integer getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(Integer idFormula) {
        this.idFormula = idFormula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public CitaResponseDTO getCita() {
        return cita;
    }

    public void setCita(CitaResponseDTO cita) {
        this.cita = cita;
    }
}
