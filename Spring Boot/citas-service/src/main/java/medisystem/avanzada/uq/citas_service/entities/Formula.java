package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "formulas")
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormula;

    private int idCita;

    private LocalDate fecha;

    public Formula() {}

    public Formula(int idFormula, int idCita, LocalDate fecha) {
        this.idFormula = idFormula;
        this.idCita = idCita;
        this.fecha = fecha;
    }

    public int getIdFormula() {
        return idFormula;
    }

    public void setIdFormula(int idFormula) {
        this.idFormula = idFormula;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
