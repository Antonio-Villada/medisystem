package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "pacientes_telefonos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_paciente", "id_telefono"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(of = {"paciente", "telefono"})
public class PacienteTelefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente_telefono")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_telefono", nullable = false)
    private Telefono telefono;

}