package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicos")

public class Medico {

    public Medico() {
    }

    public Medico(Long idMedico, String nombreMedico, Especialidad especialidad, String telefono, String correo) {
        this.idMedico = idMedico;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.correo = correo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    private String nombreMedico;

    @ManyToOne
    @JoinColumn(name = "especialidad_id") // nombre de la FK en la tabla Medico
    private Especialidad especialidad;
    private String telefono;
    private String correo;

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
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

