package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

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
