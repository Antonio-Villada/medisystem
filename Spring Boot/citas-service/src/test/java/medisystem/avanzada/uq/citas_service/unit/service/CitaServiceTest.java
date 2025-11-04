package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.cita.CitaRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.cita.CitaResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Cita;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.Paciente;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.exceptions.CitaConflictoHorarioException;
import medisystem.avanzada.uq.citas_service.mappers.CitaMapper;
import medisystem.avanzada.uq.citas_service.repositories.CitaRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.repositories.PacienteRepository;
import medisystem.avanzada.uq.citas_service.services.impl.CitaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek; // <-- SOLUCIÓN AL ERROR 1
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    // Mocks
    @Mock private CitaRepository citaRepository;
    @Mock private MedicoRepository medicoRepository;
    @Mock private PacienteRepository pacienteRepository;
    @Mock private CitaMapper citaMapper;

    // Mocks de Seguridad
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    // Clase a Probar
    @InjectMocks
    private CitaServiceImpl citaService;

    // Variables de Prueba
    private final Long CITA_ID = 1L;
    private final Long MEDICO_ID = 10L;
    private final String PACIENTE_ID = "123456";
    private final String AUTH_USERNAME = "paciente1";

    // Aseguramos que la fecha sea futura y no un fin de semana
    private final LocalDate FECHA_VALIDA = LocalDate.now().plusDays(3).getDayOfWeek() == DayOfWeek.SATURDAY ?
            LocalDate.now().plusDays(5) :
            LocalDate.now().plusDays(3).getDayOfWeek() == DayOfWeek.SUNDAY ?
                    LocalDate.now().plusDays(4) :
                    LocalDate.now().plusDays(3);
    private final LocalTime HORA_VALIDA = LocalTime.of(10, 0);

    private CitaRequestDTO requestDTO;
    private CitaResponseDTO responseDTO;
    private Medico medicoEntity;
    private Paciente pacienteEntity;
    private Cita citaEntity;

    @BeforeEach
    void setUp() {
        // 1. Simular usuario autenticado
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(AUTH_USERNAME);

        // 2. Entidades
        Usuario usuarioPaciente = new Usuario();
        usuarioPaciente.setUsername(AUTH_USERNAME);

        medicoEntity = new Medico();
        medicoEntity.setIdMedico(MEDICO_ID);

        pacienteEntity = new Paciente();
        pacienteEntity.setIdPaciente(PACIENTE_ID);
        pacienteEntity.setUsuario(usuarioPaciente);

        citaEntity = new Cita();
        citaEntity.setIdCita(CITA_ID);
        citaEntity.setMedico(medicoEntity);
        citaEntity.setPaciente(pacienteEntity);

        // 3. DTOs
        requestDTO = new CitaRequestDTO();
        requestDTO.setIdMedico(MEDICO_ID);
        requestDTO.setIdPaciente(PACIENTE_ID);
        requestDTO.setFecha(FECHA_VALIDA);
        requestDTO.setHoraInicio(HORA_VALIDA);

        // SOLUCIÓN AL ERROR 2: Se alinea la llamada al constructor con los 8 campos del DTO
        responseDTO = new CitaResponseDTO(
                CITA_ID,
                FECHA_VALIDA,
                HORA_VALIDA,
                HORA_VALIDA.plusHours(1),
                "Observaciones de prueba",
                null, // MedicoResponseDTO (mock)
                null, // PacienteResponseDTO (mock)
                null  // FormulaResponseDTO (mock)
        );
    }

    // --------------------------------------------------------------------
    // TEST 1: agendarCita - Flujo Exitoso
    // --------------------------------------------------------------------
    @Test
    void agendarCita_deberiaAgendarCita_cuandoEsValidaYUnica() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medicoEntity));
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(pacienteEntity));

        // SOLUCIÓN AL ERROR 3: .thenReturn(0) (int)
        when(citaRepository.countByMedicoAndFecha(medicoEntity, FECHA_VALIDA)).thenReturn(0);
        when(citaRepository.existsByMedicoAndFechaAndHoraInicio(medicoEntity, FECHA_VALIDA, HORA_VALIDA)).thenReturn(false);

        // Mapeo y Guardado
        when(citaMapper.toEntity(requestDTO, medicoEntity, pacienteEntity)).thenReturn(citaEntity);
        when(citaRepository.save(citaEntity)).thenReturn(citaEntity);
        when(citaMapper.toResponseDTO(citaEntity)).thenReturn(responseDTO);

        // Act
        CitaResponseDTO resultado = citaService.agendarCita(requestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(CITA_ID, resultado.getIdCita());
        verify(citaRepository, times(1)).save(citaEntity);
    }

    // --------------------------------------------------------------------
    // TEST 2: agendarCita - Fallo por Limite Diario Alcanzado
    // --------------------------------------------------------------------
    @Test
    void agendarCita_deberiaLanzarExcepcion_cuandoLimiteDiarioAlcanzado() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medicoEntity));
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(pacienteEntity));

        // SOLUCIÓN AL ERROR 3: .thenReturn(10) (int)
        when(citaRepository.countByMedicoAndFecha(medicoEntity, FECHA_VALIDA)).thenReturn(10);
        when(citaRepository.existsByMedicoAndFechaAndHoraInicio(any(), any(), any())).thenReturn(false);

        // Act & Assert
        assertThrows(CitaConflictoHorarioException.class, () -> {
            citaService.agendarCita(requestDTO);
        });

        verify(citaRepository, never()).save(any());
    }
}