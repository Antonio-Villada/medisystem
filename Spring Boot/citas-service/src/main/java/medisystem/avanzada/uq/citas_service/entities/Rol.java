package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)  // <-- guarda el nombre del enum como texto (MEDICO, PACIENTE, ADMINISTRADOR)
    @Column(unique = true, nullable = false)
    private RolNombre nombre;

    public Rol() {}

    public Rol(RolNombre nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RolNombre getNombre() { return nombre; }
    public void setNombre(RolNombre nombre) { this.nombre = nombre; }
}
