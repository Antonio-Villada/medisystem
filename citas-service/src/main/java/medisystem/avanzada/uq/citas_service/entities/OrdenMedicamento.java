package medisystem.avanzada.uq.citas_service.entities;



import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ordenes_medicamentos")
public class OrdenMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long omId;

    private String dosis;
    private String indicaciones;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenMedica ordenMedica;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    public Long getOmId() {
        return omId;
    }

    public void setOmId(Long omId) {
        this.omId = omId;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public OrdenMedica getOrdenMedica() {
        return ordenMedica;
    }

    public void setOrdenMedica(OrdenMedica ordenMedica) {
        this.ordenMedica = ordenMedica;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
}
