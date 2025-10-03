package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    private String idPaciente;

    private String nombrePaciente;
    private Integer ciudad;
    private String correo;
    private Integer eps;
    public Paciente() {
    }

    public Paciente(String idPaciente, String nombrePaciente, Integer ciudad, String correo, Integer eps) {
        this.idPaciente = idPaciente;
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Integer getCiudad() {
        return ciudad;
    }

    public void setCiudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEps() {
        return eps;
    }

    public void setEps(Integer eps) {
        this.eps = eps;
    }
}
