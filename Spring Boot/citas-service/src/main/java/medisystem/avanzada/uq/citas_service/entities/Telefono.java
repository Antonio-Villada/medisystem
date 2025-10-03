package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "telefonos")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTelefono;


    private String telefono;

    public Telefono() {
    }

    public Telefono(Integer idTelefono, String telefono) {
        this.idTelefono = idTelefono;
        this.telefono = telefono;
    }

    public Integer getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Integer idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
