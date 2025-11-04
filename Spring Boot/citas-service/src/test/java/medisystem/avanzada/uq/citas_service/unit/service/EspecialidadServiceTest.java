package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.especialidad.EspecialidadResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Especialidad;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.EspecialidadYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.EspecialidadMapper;
import medisystem.avanzada.uq.citas_service.repositories.EspecialidadRepository;
import medisystem.avanzada.uq.citas_service.services.impl.EspecialidadServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EspecialidadServiceTest {

    // 1. Componentes Falsos (Mocks)
    @Mock
    private EspecialidadRepository especialidadRepository;

    @Mock
    private EspecialidadMapper especialidadMapper;

    // 2. Clase a Probar (Se inyectan automáticamente los mocks)
    @InjectMocks
    private EspecialidadServiceImpl especialidadService;

    // 3. Variables de Prueba
    private final Long ESP_ID = 5L;
    private final String ESP_NOMBRE = "CARDIOLOGÍA";
    private Especialidad especialidadEntity;
    private EspecialidadRequestDTO especialidadRequestDTO;
    private EspecialidadResponseDTO especialidadResponseDTO;

    @BeforeEach
    void setUp() {
        // Inicializar entidades y DTOs comunes antes de cada prueba
        especialidadEntity = new Especialidad();
        especialidadEntity.setIdEspecialidad(ESP_ID);
        especialidadEntity.setNombreEspecialidad(ESP_NOMBRE);

        especialidadRequestDTO = new EspecialidadRequestDTO();
        especialidadRequestDTO.setNombreEspecialidad(ESP_NOMBRE);

        especialidadResponseDTO = new EspecialidadResponseDTO(ESP_ID, ESP_NOMBRE);
    }

    // --------------------------------------------------------------------
    // TEST 1: postEspecialidad - Creación Exitosa
    // --------------------------------------------------------------------
    @Test
    void postEspecialidad_deberiaCrearEspecialidad_cuandoNombreEsUnico() {
        // Arrange
        // 1. La especialidad NO existe
        when(especialidadRepository.existsByNombreEspecialidad(ESP_NOMBRE)).thenReturn(false);
        // 2. Mapeo Request -> Entidad
        when(especialidadMapper.toEntity(especialidadRequestDTO)).thenReturn(especialidadEntity);
        // 3. Guardado exitoso y devolución
        when(especialidadRepository.save(especialidadEntity)).thenReturn(especialidadEntity);
        // 4. Mapeo Entidad -> Response
        when(especialidadMapper.toResponseDTO(especialidadEntity)).thenReturn(especialidadResponseDTO);

        // Act
        EspecialidadResponseDTO resultado = especialidadService.postEspecialidad(especialidadRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(ESP_ID, resultado.getIdEspecialidad());
        // Verificar interacciones clave
        verify(especialidadRepository, times(1)).existsByNombreEspecialidad(ESP_NOMBRE);
        verify(especialidadRepository, times(1)).save(especialidadEntity);
    }

    // --------------------------------------------------------------------
    // TEST 2: postEspecialidad - Creación con Nombre Duplicado (Excepción)
    // --------------------------------------------------------------------
    @Test
    void postEspecialidad_deberiaLanzarExcepcion_cuandoEspecialidadYaExiste() {
        // Arrange
        // 1. La especialidad YA existe
        when(especialidadRepository.existsByNombreEspecialidad(ESP_NOMBRE)).thenReturn(true);

        // Act & Assert
        // Verificar que se lanza la excepción correcta
        assertThrows(EspecialidadYaExisteException.class, () -> {
            especialidadService.postEspecialidad(especialidadRequestDTO);
        });

        // Verificar que el método save NUNCA fue llamado
        verify(especialidadRepository, never()).save(any(Especialidad.class));
    }

    // --------------------------------------------------------------------
    // TEST 3: getEspecialidadById - Búsqueda Exitosa
    // --------------------------------------------------------------------
    @Test
    void getEspecialidadById_deberiaRetornarEspecialidad_cuandoExiste() {
        // Arrange
        // 1. Repositorio devuelve la entidad
        when(especialidadRepository.findById(ESP_ID)).thenReturn(Optional.of(especialidadEntity));
        // 2. Mapeo Entidad -> Response
        when(especialidadMapper.toResponseDTO(especialidadEntity)).thenReturn(especialidadResponseDTO);

        // Act
        EspecialidadResponseDTO resultado = especialidadService.getEspecialidadById(ESP_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(ESP_ID, resultado.getIdEspecialidad());
        verify(especialidadRepository, times(1)).findById(ESP_ID);
    }

    // --------------------------------------------------------------------
    // TEST 4: getEspecialidadById - Búsqueda No Encontrada (Excepción)
    // --------------------------------------------------------------------
    @Test
    void getEspecialidadById_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        // 1. Repositorio devuelve Optional vacío
        when(especialidadRepository.findById(ESP_ID)).thenReturn(Optional.empty());

        // Act & Assert
        // Verificar que se lanza la excepción de No Encontrada
        assertThrows(EspecialidadNoEncontradaException.class, () -> {
            especialidadService.getEspecialidadById(ESP_ID);
        });
    }

    // --------------------------------------------------------------------
    // TEST 5: getAllEspecialidades - Retorno de Lista
    // --------------------------------------------------------------------
    @Test
    void getAllEspecialidades_deberiaRetornarListaDeEspecialidades() {
        // Arrange
        List<Especialidad> especialidadList = List.of(especialidadEntity);
        // 1. Repositorio devuelve la lista
        when(especialidadRepository.findAll()).thenReturn(especialidadList);
        // 2. Mapeo de la lista
        when(especialidadMapper.toResponseDTO(especialidadEntity)).thenReturn(especialidadResponseDTO);

        // Act
        List<EspecialidadResponseDTO> resultado = especialidadService.getAllEspecialidades();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(especialidadRepository, times(1)).findAll();
    }
}