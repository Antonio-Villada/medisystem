package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;

import java.math.BigDecimal; // Importar el tipo de dato correcto para dinero

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "medicamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nombreMedicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Long idMedicamento;

    @Column(name = "nombre_medicamento", length = 100, nullable = false, unique = true)
    private String nombreMedicamento;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

}