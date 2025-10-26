package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Usuario;


@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor // Genera el constructor vacío
@AllArgsConstructor // Genera el constructor con todos los argumentos
@EqualsAndHashCode(of = "correo") // Implementa equals y hashCode basado en 'correo'
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "nombre_medico", nullable = false)
    private String nombreMedico;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

}