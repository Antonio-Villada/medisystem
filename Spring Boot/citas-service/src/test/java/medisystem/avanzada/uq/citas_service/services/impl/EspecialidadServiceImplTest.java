package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class EspecialidadServiceImplTest {

    @Autowired
    private EspecialidadServiceImpl especialidadService;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    private Especialidad especialidad1;
    private Especialidad especialidad2;

    @BeforeEach
    void setUp() {
        especialidad1 = new Especialidad();
        especialidad1.setNombreEspecialidad("Cardiología");
        especialidad1 = especialidadRepository.save(especialidad1);

        especialidad2 = new Especialidad();
        especialidad2.setNombreEspecialidad("Dermatología");
        especialidad2 = especialidadRepository.save(especialidad2);
    }

    @Test
    void getEspecialidades() {
        List<Especialidad> result = especialidadService.getEspecialidades();
        assertNotNull(result);
        assertTrue(result.stream().anyMatch(e -> e.getNombreEspecialidad().equals("Cardiología")));
        assertTrue(result.stream().anyMatch(e -> e.getNombreEspecialidad().equals("Dermatología")));
    }

    @Test
    void getEspecialidadByid() {
        Especialidad result = especialidadService.getEspecialidadByid(especialidad1.getIdEspecialidad());
        assertNotNull(result);
        assertEquals("Cardiología", result.getNombreEspecialidad());

        assertThrows(EspecialidadNoEncontradaException.class,
                () -> especialidadService.getEspecialidadByid(999999L));
    }

    @Test
    void postEspecialidad() {
        Especialidad nueva = new Especialidad();
        nueva.setNombreEspecialidad("Neurología");
        Especialidad result = especialidadService.postEspecialidad(nueva);

        assertNotNull(result);
        assertNotNull(result.getIdEspecialidad());
        assertEquals("Neurología", result.getNombreEspecialidad());
    }

    @Test
    void putEspecialidad() {
        Especialidad updated = new Especialidad();
        updated.setNombreEspecialidad("Cardiología Avanzada");

        Especialidad result = especialidadService.putEspecialidad(especialidad1.getIdEspecialidad(), updated);
        assertEquals("Cardiología Avanzada", result.getNombreEspecialidad());

        assertThrows(EspecialidadNoEncontradaException.class,
                () -> especialidadService.putEspecialidad(999999L, updated));
    }

    @Test
    void deleteEspecialidad() {
        Especialidad temporal = new Especialidad();
        temporal.setNombreEspecialidad("Temporal");
        temporal = especialidadRepository.save(temporal);

        especialidadService.deleteEspecialidad(temporal.getIdEspecialidad());
        assertFalse(especialidadRepository.existsById(temporal.getIdEspecialidad()));

        assertThrows(EspecialidadNoEncontradaException.class,
                () -> especialidadService.deleteEspecialidad(999999L));
    }

    // ===== Separando casos del patch para evitar conflictos =====
    @Test
    void pachEspecialidad_ShouldUpdateNombre_WhenNotNull() {
        Especialidad parcial = new Especialidad();
        parcial.setNombreEspecialidad("Cardio Parcial");

        Especialidad result = especialidadService.pachEspecialidad(especialidad1.getIdEspecialidad(), parcial);
        assertEquals("Cardio Parcial", result.getNombreEspecialidad());
    }

    @Test
    void pachEspecialidad_ShouldNotChangeNombre_WhenNull() {
        Especialidad parcialNull = new Especialidad();

        Especialidad result = especialidadService.pachEspecialidad(especialidad1.getIdEspecialidad(), parcialNull);
        assertEquals("Cardiología", result.getNombreEspecialidad());
    }

    @Test
    void pachEspecialidad_WithInvalidId_ShouldThrowException() {
        Especialidad parcial = new Especialidad();
        parcial.setNombreEspecialidad("Inexistente");

        assertThrows(EspecialidadNoEncontradaException.class,
                () -> especialidadService.pachEspecialidad(999999L, parcial));
    }
}
