//package medisystem.avanzada.uq.citas_service.unit.service;
//
//import medisystem.avanzada.uq.citas_service.entities.Telefono;
//import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
//import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.impl.TelefonoServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.annotation.Import;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@Import(TestSecurityConfig.class)
//class TelefonoServiceImplTest {
//
//    @Mock
//    private TelefonoRepository telefonoRepository;
//
//    @InjectMocks
//    private TelefonoServiceImpl telefonoService;
//
//    private Telefono telefono;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        telefono = new Telefono(1, "3001234567");
//    }
//
//    @Test
//    void getTelefonos() {
//        when(telefonoRepository.findAll()).thenReturn(List.of(telefono));
//
//        List<Telefono> result = telefonoService.getTelefonos();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("3001234567", result.get(0).getTelefono());
//        verify(telefonoRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getTelefonoById() {
//        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono));
//
//        Telefono result = telefonoService.getTelefonoById(1);
//
//        assertNotNull(result);
//        assertEquals("3001234567", result.getTelefono());
//        verify(telefonoRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void getTelefonoById_inexistente() {
//        when(telefonoRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(TelefonoNoEncontradoException.class, () ->
//                telefonoService.getTelefonoById(99));
//    }
//
//    @Test
//    void postTelefono() {
//        when(telefonoRepository.save(telefono)).thenReturn(telefono);
//
//        Telefono result = telefonoService.postTelefono(telefono);
//
//        assertEquals("3001234567", result.getTelefono());
//        verify(telefonoRepository).save(telefono);
//    }
//
//    @Test
//    void putTelefono() {
//        Telefono nuevo = new Telefono(1, "3107654321");
//        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono));
//        when(telefonoRepository.save(any())).thenReturn(nuevo);
//
//        Telefono result = telefonoService.putTelefono(1, nuevo);
//
//        assertEquals("3107654321", result.getTelefono());
//        verify(telefonoRepository).save(any());
//    }
//
//    @Test
//    void putTelefono_inexistente() {
//        when(telefonoRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(TelefonoNoEncontradoException.class, () ->
//                telefonoService.putTelefono(99, telefono));
//    }
//
//    @Test
//    void deleteTelefono() {
//        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono));
//
//        telefonoService.deleteTelefono(1);
//
//        verify(telefonoRepository).delete(telefono);
//    }
//
//    @Test
//    void deleteTelefono_inexistente() {
//        when(telefonoRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(TelefonoNoEncontradoException.class, () ->
//                telefonoService.deleteTelefono(99));
//    }
//
//    @Test
//    void patchTelefono() {
//        Telefono patch = new Telefono();
//        patch.setTelefono("3119876543");
//        when(telefonoRepository.findById(1)).thenReturn(Optional.of(telefono));
//        when(telefonoRepository.save(any())).thenReturn(patch);
//
//        Telefono result = telefonoService.patchTelefono(1, patch);
//
//        assertEquals("3119876543", result.getTelefono());
//    }
//
//    @Test
//    void patchTelefono_inexistente() {
//        when(telefonoRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(TelefonoNoEncontradoException.class, () ->
//                telefonoService.patchTelefono(99, telefono));
//    }
//}
