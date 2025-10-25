package medisystem.avanzada.uq.citas_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    private String idPaciente;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombrePaciente;
    private String ciudad;
    private String correo;

    @ManyToOne
    @JoinColumn(name = "id_eps", nullable = false)
    private Eps eps;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PacienteTelefono> telefonos;

    public List<String> getTelefonos() {
        if (telefonos == null) return List.of();
        return telefonos.stream()
                .map(pt -> pt.getTelefono().getTelefono()) // ajustado según tu entidad Telefono
                .collect(Collectors.toList());
    }

    public void setTelefonos(List<PacienteTelefono> telefonos) {
        this.telefonos = telefonos;
    }

    public Paciente() {
    }

    public Paciente(String idPaciente, Usuario usuario, String nombrePaciente, String ciudad, String correo, Eps eps) {
        this.idPaciente = idPaciente;
        this.usuario = usuario;
        this.nombrePaciente = nombrePaciente;
        this.ciudad = ciudad;
        this.correo = correo;
        this.eps = eps;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
