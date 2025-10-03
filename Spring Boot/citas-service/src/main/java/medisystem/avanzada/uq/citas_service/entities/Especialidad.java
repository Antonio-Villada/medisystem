package medisystem.avanzada.uq.citas_service.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "especialidades")

public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    private String nombreEspecialidad;

    public Especialidad() {
    }

    public Especialidad(Long idEspecialidad, String nombreEspecialidad) {
        this.idEspecialidad = idEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
}
