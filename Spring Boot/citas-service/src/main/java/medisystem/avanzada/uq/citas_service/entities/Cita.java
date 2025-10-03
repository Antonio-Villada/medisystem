package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita; // int(11)

    @Column(nullable = false)
    private LocalDate fecha; // date

    @Column(length = 20, nullable = false)
    private String idMedico; // varchar(20)

    @Column(length = 20, nullable = false)
    private String idPaciente; // varchar(20)

    @Lob
    private String observaciones; // text

    @Column(nullable = false)
    private Integer idFormula; // int(11)

    public Cita() {
    }

    public Cita(Integer idCita, LocalDate fecha, String idMedico, String idPaciente, String observaciones, Integer idFormula) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.observaciones = observaciones;
        this.idFormula = idFormula;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(Integer idFormula) {
        this.idFormula = idFormula;
    }
}
