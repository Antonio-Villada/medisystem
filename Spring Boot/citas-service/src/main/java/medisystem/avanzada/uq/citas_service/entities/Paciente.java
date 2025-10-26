package medisystem.avanzada.uq.citas_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.entities.Usuario;

import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPaciente") // Usamos el ID natural (cédula) como clave
public class Paciente {

    @Id
    // Asumimos que el ID (cédula/identificación) es un valor natural y no se autogenera.
    @Column(name = "id_paciente", nullable = false, unique = true)
    private String idPaciente;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nombre_paciente", nullable = false)
    private String nombrePaciente;

    @Column(name = "ciudad", nullable = false) // Asumimos que la ciudad es requerida
    private String ciudad;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_eps", nullable = false)
    private Eps eps;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore // Previene bucles infinitos al serializar a JSON
    private List<PacienteTelefono> telefonos;


}