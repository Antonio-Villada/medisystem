package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
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
class TelefonoServiceImplTest {

    @Autowired
    private TelefonoServiceImpl telefonoService;

    @Autowired
    private TelefonoRepository telefonoRepository;

    private Telefono telefono1;
    private Telefono telefono2;

    @BeforeEach
    void setUp() {
        // Limpiar datos de prueba existentes
        telefonoRepository.deleteAll();

        // Crear y guardar nuevos teléfonos de prueba
        telefono1 = new Telefono();
        telefono1.setTelefono("123456789");
        telefono1 = telefonoRepository.save(telefono1);

        telefono2 = new Telefono();
        telefono2.setTelefono("987654321");
        telefono2 = telefonoRepository.save(telefono2);
    }

    @Test
    void getTelefonos_ShouldReturnAllTelefonos() {
        // When
        List<Telefono> result = telefonoService.getTelefonos();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getTelefono().equals("123456789")));
        assertTrue(result.stream().anyMatch(t -> t.getTelefono().equals("987654321")));
    }

    @Test
    void getTelefonoById_WithValidId_ShouldReturnTelefono() {
        // When
        Telefono result = telefonoService.getTelefonoById(telefono1.getIdTelefono());

        // Then
        assertNotNull(result);
        assertEquals(telefono1.getIdTelefono(), result.getIdTelefono());
        assertEquals("123456789", result.getTelefono());
    }

    @Test
    void getTelefonoById_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidId = 999999; // ID que no existe

        // When & Then
        assertThrows(TelefonoNoEncontradoException.class,
                () -> telefonoService.getTelefonoById(invalidId));
    }

    @Test
    void postTelefono_ShouldSaveAndReturnTelefono() {
        // Given
        Telefono nuevoTelefono = new Telefono();
        nuevoTelefono.setTelefono("555555555");

        // When
        Telefono result = telefonoService.postTelefono(nuevoTelefono);

        // Then
        assertNotNull(result);
        assertNotNull(result.getIdTelefono());
        assertEquals("555555555", result.getTelefono());

        // Verificar que se guardó en la base de datos
        Telefono savedTelefono = telefonoRepository.findById(result.getIdTelefono()).orElse(null);
        assertNotNull(savedTelefono);
        assertEquals("555555555", savedTelefono.getTelefono());
    }

    @Test
    void putTelefono_WithValidId_ShouldUpdateTelefono() {
        // Given
        Telefono telefonoActualizado = new Telefono();
        telefonoActualizado.setTelefono("999999999");

        // When
        Telefono result = telefonoService.putTelefono(telefono1.getIdTelefono(), telefonoActualizado);

        // Then
        assertNotNull(result);
        assertEquals(telefono1.getIdTelefono(), result.getIdTelefono());
        assertEquals("999999999", result.getTelefono());

        // Verificar que se actualizó en la base de datos
        Telefono updatedTelefono = telefonoRepository.findById(telefono1.getIdTelefono()).orElse(null);
        assertNotNull(updatedTelefono);
        assertEquals("999999999", updatedTelefono.getTelefono());
    }

    @Test
    void putTelefono_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidId = 999999;
        Telefono telefonoActualizado = new Telefono();
        telefonoActualizado.setTelefono("999999999");

        // When & Then
        assertThrows(TelefonoNoEncontradoException.class,
                () -> telefonoService.putTelefono(invalidId, telefonoActualizado));
    }

    @Test
    void deleteTelefono_WithValidId_ShouldDeleteTelefono() {
        // When
        telefonoService.deleteTelefono(telefono1.getIdTelefono());

        // Then
        assertFalse(telefonoRepository.existsById(telefono1.getIdTelefono()));

        // Verificar que el otro teléfono sigue existiendo
        assertTrue(telefonoRepository.existsById(telefono2.getIdTelefono()));
    }

    @Test
    void deleteTelefono_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidId = 999999;

        // When & Then
        assertThrows(TelefonoNoEncontradoException.class,
                () -> telefonoService.deleteTelefono(invalidId));
    }

    @Test
    void patchTelefono_WithValidIdAndTelefono_ShouldPartiallyUpdateTelefono() {
        // Given
        Telefono telefonoParcial = new Telefono();
        telefonoParcial.setTelefono("111111111");

        // When
        Telefono result = telefonoService.patchTelefono(telefono1.getIdTelefono(), telefonoParcial);

        // Then
        assertNotNull(result);
        assertEquals(telefono1.getIdTelefono(), result.getIdTelefono());
        assertEquals("111111111", result.getTelefono());

        // Verificar que se actualizó en la base de datos
        Telefono updatedTelefono = telefonoRepository.findById(telefono1.getIdTelefono()).orElse(null);
        assertNotNull(updatedTelefono);
        assertEquals("111111111", updatedTelefono.getTelefono());
    }

    @Test
    void patchTelefono_WithValidIdAndNullTelefono_ShouldNotUpdateTelefono() {
        // Given
        Telefono telefonoParcial = new Telefono(); // telefono es null

        // When
        Telefono result = telefonoService.patchTelefono(telefono1.getIdTelefono(), telefonoParcial);

        // Then
        assertNotNull(result);
        assertEquals(telefono1.getIdTelefono(), result.getIdTelefono());
        assertEquals("123456789", result.getTelefono()); // No debería cambiar

        // Verificar en base de datos
        Telefono unchangedTelefono = telefonoRepository.findById(telefono1.getIdTelefono()).orElse(null);
        assertNotNull(unchangedTelefono);
        assertEquals("123456789", unchangedTelefono.getTelefono());
    }

    @Test
    void patchTelefono_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidId = 999999;
        Telefono telefonoParcial = new Telefono();
        telefonoParcial.setTelefono("111111111");

        // When & Then
        assertThrows(TelefonoNoEncontradoException.class,
                () -> telefonoService.patchTelefono(invalidId, telefonoParcial));
    }

    @Test
    void patchTelefono_WithEmptyTelefono_ShouldUpdateTelefono() {
        // Given
        Telefono telefonoParcial = new Telefono();
        telefonoParcial.setTelefono(""); // String vacío

        // When
        Telefono result = telefonoService.patchTelefono(telefono1.getIdTelefono(), telefonoParcial);

        // Then
        assertNotNull(result);
        assertEquals(telefono1.getIdTelefono(), result.getIdTelefono());
        assertEquals("", result.getTelefono());
    }
}