package medisystem.avanzada.uq.citas_service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCita") // Basado en el ID
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "estado", columnDefinition = "TEXT")
    private String estado;

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) // AÑADIDO: LAZY y orphanRemoval
    private Formula formula;

    @PrePersist
    @PreUpdate
    private void calcularHoraFin() {
        if (this.horaInicio != null) {
            this.horaFin = this.horaInicio.plusHours(1);
        }
    }

}