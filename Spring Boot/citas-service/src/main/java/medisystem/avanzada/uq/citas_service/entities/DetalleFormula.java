package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "detalle_formula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idDetalleFormula")
public class DetalleFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_formula")
    private Long idDetalleFormula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formula", nullable = false)
    private Formula formula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "dosis", nullable = false, length = 255)
    private String dosis;


}