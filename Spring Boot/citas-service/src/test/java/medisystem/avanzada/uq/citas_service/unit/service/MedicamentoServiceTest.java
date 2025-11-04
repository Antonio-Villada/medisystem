package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.medicamento.MedicamentoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Medicamento;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.MedicamentoYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.MedicamentoMapper;
import medisystem.avanzada.uq.citas_service.repositories.MedicamentoRepository;
import medisystem.avanzada.uq.citas_service.services.impl.MedicamentoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicamentoServiceTest {

    // 1. Componentes Falsos (Mocks)
    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private MedicamentoMapper medicamentoMapper;

    // 2. Clase a Probar
    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    // 3. Variables de Prueba
    private final Long MED_ID = 20L;
    private final String MED_NOMBRE = "IBUPROFENO";
    private final BigDecimal MED_PRECIO = new BigDecimal("5000.00");
    private Medicamento medicamentoEntity;
    private MedicamentoRequestDTO medicamentoRequestDTO;
    private MedicamentoResponseDTO medicamentoResponseDTO;

    @BeforeEach
    void setUp() {
        // Inicializar entidades y DTOs
        medicamentoEntity = new Medicamento();
        medicamentoEntity.setIdMedicamento(MED_ID);
        medicamentoEntity.setNombreMedicamento(MED_NOMBRE);
        medicamentoEntity.setPrecio(MED_PRECIO);

        medicamentoRequestDTO = new MedicamentoRequestDTO();
        medicamentoRequestDTO.setNombreMedicamento(MED_NOMBRE);
        medicamentoRequestDTO.setPrecio(MED_PRECIO);

        medicamentoResponseDTO = new MedicamentoResponseDTO(MED_ID, MED_NOMBRE, MED_PRECIO);
    }

    // --------------------------------------------------------------------
    // TEST 1: postMedicamento - Creación Exitosa
    // --------------------------------------------------------------------
    @Test
    void postMedicamento_deberiaCrearMedicamento_cuandoNombreEsUnico() {
        // Arrange
        // Usamos existsByNombreMedicamento de tu repositorio
        when(medicamentoRepository.existsByNombreMedicamento(MED_NOMBRE)).thenReturn(false);
        when(medicamentoMapper.toEntity(medicamentoRequestDTO)).thenReturn(medicamentoEntity);
        when(medicamentoRepository.save(medicamentoEntity)).thenReturn(medicamentoEntity);
        when(medicamentoMapper.toResponseDTO(medicamentoEntity)).thenReturn(medicamentoResponseDTO);

        // Act
        MedicamentoResponseDTO resultado = medicamentoService.postMedicamento(medicamentoRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(MED_ID, resultado.getIdMedicamento());
        // Verificar interacciones clave
        verify(medicamentoRepository, times(1)).existsByNombreMedicamento(MED_NOMBRE);
        verify(medicamentoRepository, times(1)).save(medicamentoEntity);
    }

    // --------------------------------------------------------------------
    // TEST 2: postMedicamento - Creación con Nombre Duplicado (Excepción)
    // --------------------------------------------------------------------
    @Test
    void postMedicamento_deberiaLanzarExcepcion_cuandoMedicamentoYaExiste() {
        // Arrange
        // El medicamento YA existe
        when(medicamentoRepository.existsByNombreMedicamento(MED_NOMBRE)).thenReturn(true);

        // Act & Assert
        assertThrows(MedicamentoYaExisteException.class, () -> {
            medicamentoService.postMedicamento(medicamentoRequestDTO);
        });

        // Verificar que el método save NUNCA fue llamado
        verify(medicamentoRepository, never()).save(any(Medicamento.class));
    }

    // --------------------------------------------------------------------
    // TEST 3: getMedicamentoById - Búsqueda Exitosa
    // --------------------------------------------------------------------
    @Test
    void getMedicamentoById_deberiaRetornarMedicamento_cuandoExiste() {
        // Arrange
        when(medicamentoRepository.findById(MED_ID)).thenReturn(Optional.of(medicamentoEntity));
        when(medicamentoMapper.toResponseDTO(medicamentoEntity)).thenReturn(medicamentoResponseDTO);

        // Act
        MedicamentoResponseDTO resultado = medicamentoService.getMedicamentoById(MED_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(MED_ID, resultado.getIdMedicamento());
        verify(medicamentoRepository, times(1)).findById(MED_ID);
    }

    // --------------------------------------------------------------------
    // TEST 4: getMedicamentoById - Búsqueda No Encontrada (Excepción)
    // --------------------------------------------------------------------
    @Test
    void getMedicamentoById_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        when(medicamentoRepository.findById(MED_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MedicamentoNoEncontradoException.class, () -> {
            medicamentoService.getMedicamentoById(MED_ID);
        });
    }

    // --------------------------------------------------------------------
    // TEST 5: deleteMedicamento - Eliminación No Encontrada (Excepción)
    // --------------------------------------------------------------------
    @Test
    void deleteMedicamento_deberiaLanzarExcepcion_cuandoNoExiste() {
        // Arrange
        when(medicamentoRepository.existsById(MED_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(MedicamentoNoEncontradoException.class, () -> {
            medicamentoService.deleteMedicamento(MED_ID);
        });

        verify(medicamentoRepository, never()).deleteById(anyLong());
    }
}