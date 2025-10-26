package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor // Constructor vacío para JPA
@EqualsAndHashCode(of = "nombre") // Usa el nombre
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", unique = true, nullable = false)
    private RolNombre nombre;


    public Rol(RolNombre nombre) {
        this.nombre = nombre;
    }

}