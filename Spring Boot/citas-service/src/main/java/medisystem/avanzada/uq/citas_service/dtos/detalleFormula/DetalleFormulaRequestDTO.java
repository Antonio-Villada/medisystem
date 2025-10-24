package medisystem.avanzada.uq.citas_service.dtos.detalleFormula;

public class DetalleFormulaRequestDTO {

    private Integer idFormula;
    private Integer idMedicamento;
    private Integer cantidad;
    private String dosis;

    public DetalleFormulaRequestDTO() {}

    public Integer getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(Integer idFormula) {
        this.idFormula = idFormula;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
