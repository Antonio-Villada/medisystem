package medisystem.avanzada.uq.citas_service.dto;

public class DetalleFormulaDTO {
    private Integer idDetalleFormula;
    private Integer idFormula;
    private Integer idMedicamento;
    private String nombreMedicamento;
    private Integer cantidad;
    private String dosis;

    public DetalleFormulaDTO() {}

    public Integer getIdDetalleFormula() { return idDetalleFormula; }
    public void setIdDetalleFormula(Integer idDetalleFormula) { this.idDetalleFormula = idDetalleFormula; }

    public Integer getIdFormula() { return idFormula; }
    public void setIdFormula(Integer idFormula) { this.idFormula = idFormula; }

    public Integer getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(Integer idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
}
