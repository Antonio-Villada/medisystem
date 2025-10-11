package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelefonoServiceImplTest {

    @Mock
    private TelefonoRepository telefonoRepository;

    @InjectMocks
    private TelefonoServiceImpl telefonoService;

    private Telefono telefono1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        telefono1 = new Telefono(1, "3201234567");
    }

    @Test
    void testGetTelefonos() {
        when(telefonoRepository.findAll()).thenReturn(List.of(telefono1));

        List<Telefono> telefonos = telefonoService.getTelefonos();

        assertEquals(1, telefonos.size());
        assertEquals("3201234567", telefonos.get(0).getTelefono());
        verify(telefonoRepository, times(1)).findAll();
    }

    @Test
    void testGetTelefonoById_Encontrado() {
        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono1));

        Telefono encontrado = telefonoService.getTelefonoById(1);

        assertEquals("3201234567", encontrado.getTelefono());
        verify(telefonoRepository).findById(1);
    }

    @Test
    void testGetTelefonoById_NoEncontrado() {
        when(telefonoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TelefonoNoEncontradoException.class, () -> telefonoService.getTelefonoById(99));
        verify(telefonoRepository).findById(99);
    }

    @Test
    void testPostTelefono() {
        when(telefonoRepository.save(telefono1)).thenReturn(telefono1);

        Telefono nuevo = telefonoService.postTelefono(telefono1);

        assertEquals("3201234567", nuevo.getTelefono());
        verify(telefonoRepository).save(telefono1);
    }

    @Test
    void testPutTelefono_Existente() {
        Telefono actualizado = new Telefono(null, "3109999999");
        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono1));
        when(telefonoRepository.save(any(Telefono.class))).thenReturn(telefono1);

        Telefono resultado = telefonoService.putTelefono(1, actualizado);

        assertEquals("3109999999", resultado.getTelefono());
        verify(telefonoRepository).save(telefono1);
    }

    @Test
    void testPutTelefono_NoExistente() {
        when(telefonoRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(TelefonoNoEncontradoException.class, () -> telefonoService.putTelefono(5, telefono1));
        verify(telefonoRepository).findById(5);
    }

    @Test
    void testDeleteTelefono_Existente() {
        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono1));

        telefonoService.deleteTelefono(1);

        verify(telefonoRepository).delete(telefono1);
    }

    @Test
    void testDeleteTelefono_NoExistente() {
        when(telefonoRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(TelefonoNoEncontradoException.class, () -> telefonoService.deleteTelefono(10));
        verify(telefonoRepository).findById(10);
    }

    @Test
    void testPatchTelefono() {
        Telefono parcial = new Telefono(null, "3115555555");
        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono1));
        when(telefonoRepository.save(any(Telefono.class))).thenReturn(telefono1);

        Telefono resultado = telefonoService.patchTelefono(1, parcial);

        assertEquals("3115555555", resultado.getTelefono());
        verify(telefonoRepository).save(telefono1);
    }
}
