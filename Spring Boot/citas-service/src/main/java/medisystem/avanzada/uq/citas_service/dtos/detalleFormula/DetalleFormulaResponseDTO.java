package medisystem.avanzada.uq.citas_service.dtos.detalleFormula;


import medisystem.avanzada.uq.citas_service.dtos.formula.FormulaResponseDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;

public class DetalleFormulaResponseDTO {

    private Integer idDetalleFormula;
    private Integer cantidad;
    private String dosis;
    private FormulaResponseDTO formula;
    private MedicamentoResponseDTO medicamento;

    public DetalleFormulaResponseDTO() {}

    public Integer getIdDetalleFormula() {
        return idDetalleFormula;
    }

    public void setIdDetalleFormula(Integer idDetalleFormula) {
        this.idDetalleFormula = idDetalleFormula;
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

    public FormulaResponseDTO getFormula() {
        return formula;
    }

    public void setFormula(FormulaResponseDTO formula) {
        this.formula = formula;
    }

    public MedicamentoResponseDTO getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoResponseDTO medicamento) {
        this.medicamento = medicamento;
    }
}
