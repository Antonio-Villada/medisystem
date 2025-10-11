package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "formulas")
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormula;

    @OneToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;
    private LocalDate fecha;

    public Formula() {}

    public Formula(int idFormula, Cita cita, LocalDate fecha) {
        this.idFormula = idFormula;
        this.cita = cita;
        this.fecha = fecha;
    }

    public int getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(int idFormula) {
        this.idFormula = idFormula;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
