//package medisystem.avanzada.uq.citas_service.unit.service;
//
//import medisystem.avanzada.uq.citas_service.entities.Cita;
//import medisystem.avanzada.uq.citas_service.entities.Medico;
//import medisystem.avanzada.uq.citas_service.entities.Paciente;
//import medisystem.avanzada.uq.citas_service.exceptions.CitaNoEncontradaException;
//import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
//import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
//import medisystem.avanzada.uq.citas_service.service.impl.CitaServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.annotation.Import;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@Transactional
//@Import(TestSecurityConfig.class)
//class CitaServiceImplTest {
//
//    @Mock
//    private CitaRepository citaRepository;
//
//    @InjectMocks
//    private CitaServiceImpl citaService;
//
//    private Cita cita;
//    private Medico medico;
//    private Paciente paciente;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        medico = new Medico();
//        paciente = new Paciente();
//        cita = new Cita(1, LocalDate.now().plusDays(1), LocalTime.of(9, 0), medico, paciente, "Revisión general", null);
//    }
//
//    @Test
//    void getCitas() {
//        when(citaRepository.findAll()).thenReturn(List.of(cita));
//
//        List<Cita> result = citaService.getCitas();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(cita.getIdCita(), result.get(0).getIdCita());
//        verify(citaRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getCitaById() {
//        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
//
//        Cita result = citaService.getCitaById(1);
//
//        assertNotNull(result);
//        assertEquals(1, result.getIdCita());
//        verify(citaRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void getCitaById_inexistente() {
//        when(citaRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(CitaNoEncontradaException.class, () ->
//                citaService.getCitaById(99));
//    }
//
//    @Test
//    void postCita_valida() {
//        when(citaRepository.countByMedicoAndFecha(medico, cita.getFecha())).thenReturn(5);
//        when(citaRepository.existsByMedicoAndFechaAndHoraInicio(medico, cita.getFecha(), cita.getHoraInicio())).thenReturn(false);
//        when(citaRepository.save(cita)).thenReturn(cita);
//
//        Cita result = citaService.postCita(cita);
//
//        assertNotNull(result);
//        assertEquals(LocalTime.of(10, 0), result.getHoraFin());
//        verify(citaRepository).save(cita);
//    }
//
//    @Test
//    void postCita_fueraDeHorario() {
//        cita.setHoraInicio(LocalTime.of(7, 0));
//
//        assertThrows(IllegalArgumentException.class, () ->
//                citaService.postCita(cita));
//    }
//
//    @Test
//    void putCita() {
//        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
//        when(citaRepository.existsByMedicoAndFechaAndHoraInicio(any(), any(), any())).thenReturn(false);
//        when(citaRepository.save(any())).thenReturn(cita);
//
//        Cita nueva = new Cita(1, LocalDate.now().plusDays(2), LocalTime.of(11, 0), medico, paciente, "Actualización", null);
//
//        Cita result = citaService.putCita(1, nueva);
//
//        assertEquals("Actualización", result.getObservaciones());
//        verify(citaRepository).save(any());
//    }
//
//    @Test
//    void putCita_inexistente() {
//        when(citaRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(CitaNoEncontradaException.class, () ->
//                citaService.putCita(99, cita));
//    }
//
//    @Test
//    void deleteCita() {
//        cita.setFecha(LocalDate.now().plusDays(2));
//        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
//
//        citaService.deleteCita(1);
//
//        verify(citaRepository).delete(cita);
//    }
//
//    @Test
//    void deleteCita_fechaInvalida() {
//        cita.setFecha(LocalDate.now());
//        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
//
//        assertThrows(IllegalArgumentException.class, () ->
//                citaService.deleteCita(1));
//    }
//
//    @Test
//    void deleteCita_inexistente() {
//        when(citaRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(CitaNoEncontradaException.class, () ->
//                citaService.deleteCita(99));
//    }
//
//    @Test
//    void patchCita() {
//        Cita patch = new Cita();
//        patch.setObservaciones("Cambio de hora");
//        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
//        when(citaRepository.save(any())).thenReturn(patch);
//
//        Cita result = citaService.patchCita(1, patch);
//
//        assertEquals("Cambio de hora", result.getObservaciones());
//    }
//
//    @Test
//    void patchCita_inexistente() {
//        when(citaRepository.findById(99)).thenReturn(Optional.empty());
//
//        assertThrows(CitaNoEncontradaException.class, () ->
//                citaService.patchCita(99, cita));
//    }
//}
