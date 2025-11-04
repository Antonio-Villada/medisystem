package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medico.MedicoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.entities.Medico;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import medisystem.avanzada.uq.citas_service.entities.Usuario;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicoYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.MedicoMapper;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import medisystem.avanzada.uq.citas_service.repositories.MedicoRepository;
import medisystem.avanzada.uq.citas_service.services.UserService;
import medisystem.avanzada.uq.citas_service.services.impl.MedicoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set; // Importación necesaria para Roles

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    // Mocks
    @Mock
    private MedicoRepository medicoRepository;
    @Mock
    private EspecialidadRepository especialidadRepository;
    @Mock
    private UserService userService;
    @Mock
    private MedicoMapper medicoMapper;

    // Clase a Probar
    @InjectMocks
    private MedicoServiceImpl medicoService;

    // Variables de Prueba
    private final Long MEDICO_ID = 1L;
    private final String MEDICO_CORREO = "dr.prueba@test.com";
    private final String NUEVO_CORREO = "dr.actualizado@test.com";
    private final String TELEFONO = "3001234567";
    private final Long ESP_ID = 10L;
    private final Long NUEVA_ESP_ID = 11L;
    private final String USERNAME = "drprueba";

    private Medico medicoEntity;
    private MedicoRequestDTO medicoRequestDTO;
    private MedicoResponseDTO medicoResponseDTO;
    private Usuario usuarioEntity;
    private Especialidad especialidadEntity;
    private Especialidad nuevaEspecialidadEntity;

    @BeforeEach
    void setUp() {
        // 1. Entidades
        especialidadEntity = new Especialidad(ESP_ID, "Pediatría");
        nuevaEspecialidadEntity = new Especialidad(NUEVA_ESP_ID, "Neurología");
        usuarioEntity = new Usuario();
        usuarioEntity.setUsername(USERNAME);

        medicoEntity = new Medico();
        medicoEntity.setIdMedico(MEDICO_ID);
        medicoEntity.setNombreMedico("Dr. Prueba Antiguo");
        medicoEntity.setCorreo(MEDICO_CORREO);
        medicoEntity.setTelefono(TELEFONO);
        medicoEntity.setUsuario(usuarioEntity);
        medicoEntity.setEspecialidad(especialidadEntity);

        // 2. DTO de Petición (Request) - Alineado con MedicoRequestDTO
        medicoRequestDTO = new MedicoRequestDTO();
        medicoRequestDTO.setNombreMedico("Dr. Prueba Actualizado");
        medicoRequestDTO.setCorreo(NUEVO_CORREO);
        medicoRequestDTO.setIdEspecialidad(NUEVA_ESP_ID);
        medicoRequestDTO.setUsername(USERNAME);
        medicoRequestDTO.setPassword("pass123");
        medicoRequestDTO.setTelefono(TELEFONO);

        // 3. DTO de Respuesta (Response) - CORREGIDO
        // Alineado con el constructor de MedicoResponseDTO (7 argumentos)
        medicoResponseDTO = new MedicoResponseDTO(
                MEDICO_ID,
                "Dr. Prueba Actualizado",
                TELEFONO,
                NUEVO_CORREO,
                "Neurología", // nombreEspecialidad
                USERNAME,
                Set.of("MEDICO") // roles
        );
    }

    // ====================================================================
    // TESTS DE REGISTRO (POST)
    // ====================================================================

    @Test
    void registrarMedico_deberiaCrearMedicoYUsuario_cuandoDatosSonValidos() {
        // Arrange
        when(medicoRepository.existsByCorreo(NUEVO_CORREO)).thenReturn(false);
        when(especialidadRepository.findById(NUEVA_ESP_ID)).thenReturn(Optional.of(nuevaEspecialidadEntity));
        when(userService.crearNuevoUsuario(USERNAME, "pass123", RolNombre.MEDICO)).thenReturn(usuarioEntity);
        when(medicoMapper.toEntity(medicoRequestDTO, usuarioEntity, nuevaEspecialidadEntity)).thenReturn(medicoEntity);
        when(medicoRepository.save(medicoEntity)).thenReturn(medicoEntity);
        when(medicoMapper.toResponseDTO(medicoEntity)).thenReturn(medicoResponseDTO);

        // Act
        MedicoResponseDTO resultado = medicoService.registrarMedico(medicoRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(MEDICO_ID, resultado.getIdMedico());
        verify(medicoRepository, times(1)).existsByCorreo(NUEVO_CORREO);
        verify(medicoRepository, times(1)).save(medicoEntity);
    }

    @Test
    void registrarMedico_deberiaLanzarExcepcion_cuandoCorreoYaExiste() {
        // Arrange
        when(medicoRepository.existsByCorreo(NUEVO_CORREO)).thenReturn(true);

        // Act & Assert
        assertThrows(MedicoYaExisteException.class, () -> {
            medicoService.registrarMedico(medicoRequestDTO);
        });
        verify(userService, never()).crearNuevoUsuario(any(), any(), any());
    }

    // ====================================================================
    // TESTS DE LECTURA (GET)
    // ====================================================================

    @Test
    void getMedicoById_deberiaRetornarMedico_cuandoExiste() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medicoEntity));
        when(medicoMapper.toResponseDTO(medicoEntity)).thenReturn(medicoResponseDTO);

        // Act
        MedicoResponseDTO resultado = medicoService.getMedicoById(MEDICO_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(MEDICO_ID, resultado.getIdMedico());
    }

    @Test
    void getMedicoById_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MedicoNoEncontradoException.class, () -> {
            medicoService.getMedicoById(MEDICO_ID);
        });
    }

    // ====================================================================
    // TESTS DE ACTUALIZACIÓN (PUT)
    // ====================================================================

    @Test
    void updateMedico_deberiaActualizarMedico_cuandoDatosSonValidos() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medicoEntity));
        when(medicoRepository.existsByCorreo(NUEVO_CORREO)).thenReturn(false);
        when(especialidadRepository.findById(NUEVA_ESP_ID)).thenReturn(Optional.of(nuevaEspecialidadEntity));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoEntity);
        when(medicoMapper.toResponseDTO(medicoEntity)).thenReturn(medicoResponseDTO);

        // Act
        MedicoResponseDTO resultado = medicoService.updateMedico(MEDICO_ID, medicoRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(NUEVO_CORREO, resultado.getCorreo());
        // CORRECCIÓN: El DTO de respuesta tiene el nombre de la especialidad, no el ID.
        assertEquals("Neurología", resultado.getNombreEspecialidad());
        verify(medicoRepository, times(1)).save(medicoEntity);
    }

    @Test
    void updateMedico_deberiaLanzarExcepcion_cuandoNuevoCorreoEstaEnUso() {
        // Arrange
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medicoEntity));
        when(medicoRepository.existsByCorreo(NUEVO_CORREO)).thenReturn(true);

        // Act & Assert
        assertThrows(MedicoYaExisteException.class, () -> {
            medicoService.updateMedico(MEDICO_ID, medicoRequestDTO);
        });
        verify(medicoRepository, never()).save(any());
    }

    // ====================================================================
    // TESTS DE CONSULTA POR FILTRO
    // ====================================================================

    @Test
    void getMedicosByEspecialidadId_deberiaRetornarListaDeMedicos() {
        // Arrange
        List<Medico> medicosList = List.of(medicoEntity);
        when(especialidadRepository.findById(ESP_ID)).thenReturn(Optional.of(especialidadEntity));
        when(medicoRepository.findByEspecialidad(especialidadEntity)).thenReturn(medicosList);
        when(medicoMapper.toResponseDTO(medicoEntity)).thenReturn(medicoResponseDTO);

        // Act
        List<MedicoResponseDTO> resultado = medicoService.getMedicosByEspecialidadId(ESP_ID);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        // CORRECCIÓN: Comparamos el nombre de la especialidad del DTO de respuesta
        assertEquals("Neurología", resultado.get(0).getNombreEspecialidad());
    }
}