package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicos")

public class Medico {

    public Medico() {
    }

    public Medico(Long idMedico, String nombre, String telefono, String correo) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    private String nombre;
    //private Especialidad especialidad;
    private String telefono;
    private String correo;

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

