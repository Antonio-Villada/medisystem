package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List; // Importar List
import lombok.*;

@Entity
@Table(name = "formulas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idFormula")
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formula")
    private Long idFormula;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false, unique = true)
    private Cita cita;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    // AÑADIDO: Relación con DetalleFormula
    @OneToMany(mappedBy = "formula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFormula> detalles; // <-- Ahora la propiedad "detalles" existe en la Entidad
}