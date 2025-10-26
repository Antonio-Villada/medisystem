package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "eps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nombreEps")
public class Eps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eps")
    private Long idEps;

    @Column(name = "nombre_eps", nullable = false, unique = true, length = 100)
    private String nombreEps;

}