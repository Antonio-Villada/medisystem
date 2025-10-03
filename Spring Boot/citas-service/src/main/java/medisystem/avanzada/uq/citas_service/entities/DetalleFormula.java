package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_formula")
public class DetalleFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleFormula;

    @ManyToOne
    @JoinColumn(name = "id_formula", nullable = false)
    private Formula formula;

    @ManyToOne
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    private Integer cantidad;
    private String dosis;

    public DetalleFormula() {
    }

    public DetalleFormula(Integer idDetalleFormula, Formula formula, Medicamento medicamento, Integer cantidad, String dosis) {
        this.idDetalleFormula = idDetalleFormula;
        this.formula = formula;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.dosis = dosis;
    }

    public Integer getIdDetalleFormula() {
        return idDetalleFormula;
    }

    public void setIdDetalleFormula(Integer idDetalleFormula) {
        this.idDetalleFormula = idDetalleFormula;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
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
