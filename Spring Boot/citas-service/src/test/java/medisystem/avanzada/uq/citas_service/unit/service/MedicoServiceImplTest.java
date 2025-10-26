//package medisystem.avanzada.uq.citas_service.unit.service;
//
//import medisystem.avanzada.uq.citas_service.entities.Especialidad;
//import medisystem.avanzada.uq.citas_service.entities.Medico;
//import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
//import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.impl.MedicoServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.annotation.Import;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@Import(TestSecurityConfig.class)
//public class MedicoServiceImplTest {
//
//    @Mock
//    private MedicoRepository medicoRepository;
//
//    @InjectMocks
//    private MedicoServiceImpl medicoService;
//
//    private Medico medico;
//    private Especialidad especialidad;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        especialidad = new Especialidad(1L, "Cardiología");
//        medico = new Medico(1L, "Dr. Test", especialidad, "3100000000", "test@correo.com");
//    }
//
//    @Test
//    void getMedicos_deberiaRetornarLista() {
//        when(medicoRepository.findAll()).thenReturn(List.of(medico));
//        List<Medico> resultado = medicoService.getMedicos();
//        assertEquals(1, resultado.size());
//        verify(medicoRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getMedicoById_existente_deberiaRetornarMedico() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
//        Medico resultado = medicoService.getMedicoById(1L);
//        assertEquals("Dr. Test", resultado.getNombreMedico());
//        verify(medicoRepository).findById(1L);
//    }
//
//    @Test
//    void getMedicoById_inexistente_deberiaLanzarExcepcion() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(MedicoNoEncontradoException.class, () -> medicoService.getMedicoById(1L));
//    }
//
//    @Test
//    void postMedico_deberiaGuardarYRetornarMedico() {
//        when(medicoRepository.save(medico)).thenReturn(medico);
//        Medico guardado = medicoService.postMedico(medico);
//        assertNotNull(guardado);
//        verify(medicoRepository).save(medico);
//    }
//
//    @Test
//    void putMedico_existente_deberiaActualizarYGuardar() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
//        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);
//
//        Medico nuevo = new Medico(null, "Dr. Actualizado", especialidad, "3111111111", "nuevo@correo.com");
//        Medico actualizado = medicoService.putMedico(1L, nuevo);
//
//        assertEquals("Dr. Actualizado", actualizado.getNombreMedico());
//        verify(medicoRepository).save(any(Medico.class));
//    }
//
//    @Test
//    void putMedico_inexistente_deberiaLanzarExcepcion() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(MedicoNoEncontradoException.class, () ->
//                medicoService.putMedico(1L, medico));
//    }
//
//    @Test
//    void deleteMedico_existente_deberiaEliminar() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
//        medicoService.deleteMedico(1L);
//        verify(medicoRepository).delete(medico);
//    }
//
//    @Test
//    void deleteMedico_inexistente_deberiaLanzarExcepcion() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(MedicoNoEncontradoException.class, () -> medicoService.deleteMedico(1L));
//    }
//
//    @Test
//    void patchMedico_existente_deberiaActualizarParcialmente() {
//        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
//        when(medicoRepository.save(any())).thenReturn(medico);
//
//        Medico parcial = new Medico();
//        parcial.setTelefono("3000000000");
//        Medico resultado = medicoService.patchMedico(1L, parcial);
//
//        assertEquals("3000000000", resultado.getTelefono());
//        verify(medicoRepository).save(any());
//    }
//}
