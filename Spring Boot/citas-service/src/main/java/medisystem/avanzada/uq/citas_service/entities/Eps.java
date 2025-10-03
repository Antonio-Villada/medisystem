package medisystem.avanzada.uq.citas_service.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "eps")
public class Eps  {
    private int idEps;
    private String nombreEps;


    public Eps() {
    }

    public Eps(int idEps, String nombreEps) {
        this.idEps = idEps;
        this.nombreEps = nombreEps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdEps() {
        return idEps;
    }

    public void setIdEps(int idEps) {
        this.idEps = idEps;
    }

    public String getNombreEps() {
        return nombreEps;
    }

    public void setNombreEps(String nombreEps) {
        this.nombreEps = nombreEps;
    }
}
