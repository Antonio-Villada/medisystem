package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedicamento; // int(11)

    @Column(length = 100, nullable = false)
    private String nombreMedicamento; // varchar(100)

    @Column(nullable = false)
    private Integer precio; // int(11)

    public Medicamento() {
    }

    public Medicamento(Integer idMedicamento, String nombreMedicamento, Integer precio) {
        this.idMedicamento = idMedicamento;
        this.nombreMedicamento = nombreMedicamento;
        this.precio = precio;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
