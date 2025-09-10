package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "afiliados")

public class Afiliado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long afiliadoId;

    private String aseguradora;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "afiliado")
    private List<Cita> citas;

    @OneToMany(mappedBy = "afiliado")
    private List<OrdenMedica> ordenesMedicas;

    public Long getAfiliadoId() {
        return afiliadoId;
    }

    public void setAfiliadoId(Long afiliadoId) {
        this.afiliadoId = afiliadoId;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public List<OrdenMedica> getOrdenesMedicas() {
        return ordenesMedicas;
    }

    public void setOrdenesMedicas(List<OrdenMedica> ordenesMedicas) {
        this.ordenesMedicas = ordenesMedicas;
    }
}
