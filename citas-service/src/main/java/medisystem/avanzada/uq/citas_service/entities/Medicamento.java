package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long medicamentoId;

    @Column(name = "nombre", nullable = false, columnDefinition = "VARCHAR(255) AFTER id")
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "VARCHAR(255) AFTER nombre")
    private String descripcion;

    @OneToMany(mappedBy = "medicamento")
    private List<OrdenMedicamento> ordenesMedicamentos;

    public Long getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Long medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<OrdenMedicamento> getOrdenesMedicamentos() {
        return ordenesMedicamentos;
    }

    public void setOrdenesMedicamentos(List<OrdenMedicamento> ordenesMedicamentos) {
        this.ordenesMedicamentos = ordenesMedicamentos;
    }
}
