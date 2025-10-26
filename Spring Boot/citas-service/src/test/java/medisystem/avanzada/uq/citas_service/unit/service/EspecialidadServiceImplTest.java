//package medisystem.avanzada.uq.citas_service.unit.service;
//
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
//import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.impl.EspecialidadServiceImpl;
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
//class EspecialidadServiceImplTest {
//
//    @Mock
//    private EspecialidadRepository especialidadRepository;
//
//    @InjectMocks
//    private EspecialidadServiceImpl especialidadService;
//
//    private Especialidad especialidad;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        especialidad = new Especialidad(1L, "Cardiología");
//    }
//
//    @Test
//    void getEspecialidades() {
//        when(especialidadRepository.findAll()).thenReturn(List.of(especialidad));
//
//        List<Especialidad> result = especialidadService.getEspecialidades();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Cardiología", result.get(0).getNombreEspecialidad());
//        verify(especialidadRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getEspecialidadByid() {
//        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(especialidad));
//
//        Especialidad result = especialidadService.getEspecialidadByid(1L);
//
//        assertNotNull(result);
//        assertEquals("Cardiología", result.getNombreEspecialidad());
//        verify(especialidadRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getEspecialidadByid_inexistente() {
//        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.getEspecialidadByid(99L));
//    }
//
//    @Test
//    void postEspecialidad() {
//        when(especialidadRepository.save(especialidad)).thenReturn(especialidad);
//
//        Especialidad result = especialidadService.postEspecialidad(especialidad);
//
//        assertEquals("Cardiología", result.getNombreEspecialidad());
//        verify(especialidadRepository).save(especialidad);
//    }
//
//    @Test
//    void putEspecialidad() {
//        Especialidad nueva = new Especialidad(1L, "Neurología");
//        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(especialidad));
//        when(especialidadRepository.save(any())).thenReturn(nueva);
//
//        Especialidad result = especialidadService.putEspecialidad(1L, nueva);
//
//        assertEquals("Neurología", result.getNombreEspecialidad());
//        verify(especialidadRepository).save(any());
//    }
//
//    @Test
//    void putEspecialidad_inexistente() {
//        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.putEspecialidad(99L, especialidad));
//    }
//
//    @Test
//    void deleteEspecialidad() {
//        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(especialidad));
//
//        especialidadService.deleteEspecialidad(1L);
//
//        verify(especialidadRepository).delete(especialidad);
//    }
//
//    @Test
//    void deleteEspecialidad_inexistente() {
//        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.deleteEspecialidad(99L));
//    }
//
//    @Test
//    void pachEspecialidad() {
//        Especialidad patch = new Especialidad();
//        patch.setNombreEspecialidad("Dermatología");
//        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(especialidad));
//        when(especialidadRepository.save(any())).thenReturn(patch);
//
//        Especialidad result = especialidadService.pachEspecialidad(1L, patch);
//
//        assertEquals("Dermatología", result.getNombreEspecialidad());
//    }
//
//    @Test
//    void pachEspecialidad_inexistente() {
//        when(especialidadRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThrows(EspecialidadNoEncontradaException.class, () ->
//                especialidadService.pachEspecialidad(99L, especialidad));
//    }
//}
