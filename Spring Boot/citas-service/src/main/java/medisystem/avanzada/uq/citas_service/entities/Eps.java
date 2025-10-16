package medisystem.avanzada.uq.citas_service.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "eps")
public class Eps  {
    private Integer idEps;
    private String nombreEps;


    public Eps() {
    }

    public Eps(Integer idEps, String nombreEps) {
        this.idEps = idEps;
        this.nombreEps = nombreEps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdEps() {
        return idEps;
    }

    public void setIdEps(Integer idEps) {
        this.idEps = idEps;
    }

    public String getNombreEps() {
        return nombreEps;
    }

    public void setNombreEps(String nombreEps) {
        this.nombreEps = nombreEps;
    }
}
