package medisystem.avanzada.uq.citas_service.unit.service;

import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.security.TestSecurityConfig;
import medisystem.avanzada.uq.citas_service.service.impl.EpsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@Import(TestSecurityConfig.class)
class EpsServiceImplTest {

    @Mock
    private EpsRepository epsRepository;

    @InjectMocks
    private EpsServiceImpl epsService;

    private Eps eps;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eps = new Eps(1, "Sanitas");
    }

    @Test
    void getEps() {
        when(epsRepository.findAll()).thenReturn(List.of(eps));

        List<Eps> result = epsService.getEps();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sanitas", result.get(0).getNombreEps());
        verify(epsRepository, times(1)).findAll();
    }

    @Test
    void getEpsById() {
        when(epsRepository.findById(1)).thenReturn(Optional.of(eps));

        Eps result = epsService.getEpsById(1);

        assertNotNull(result);
        assertEquals("Sanitas", result.getNombreEps());
        verify(epsRepository, times(1)).findById(1);
    }

    @Test
    void getEpsById_inexistente() {
        when(epsRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EpsNoEncontradaException.class, () ->
                epsService.getEpsById(99));
    }

    @Test
    void postEps() {
        when(epsRepository.save(eps)).thenReturn(eps);

        Eps result = epsService.postEps(eps);

        assertEquals("Sanitas", result.getNombreEps());
        verify(epsRepository).save(eps);
    }

    @Test
    void putEps() {
        Eps nueva = new Eps(1, "Sura");
        when(epsRepository.findById(1)).thenReturn(Optional.of(eps));
        when(epsRepository.save(any())).thenReturn(nueva);

        Eps result = epsService.putEps(1, nueva);

        assertEquals("Sura", result.getNombreEps());
        verify(epsRepository).save(any());
    }

    @Test
    void putEps_inexistente() {
        when(epsRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EpsNoEncontradaException.class, () ->
                epsService.putEps(99, eps));
    }

    @Test
    void deleteEps() {
        when(epsRepository.findById(1)).thenReturn(Optional.of(eps));

        epsService.deleteEps(1);

        verify(epsRepository).delete(eps);
    }

    @Test
    void deleteEps_inexistente() {
        when(epsRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EpsNoEncontradaException.class, () ->
                epsService.deleteEps(99));
    }

    @Test
    void patchEps() {
        Eps patch = new Eps();
        patch.setNombreEps("Coomeva");
        when(epsRepository.findById(1)).thenReturn(Optional.of(eps));
        when(epsRepository.save(any())).thenReturn(patch);

        Eps result = epsService.patchEps(1, patch);

        assertEquals("Coomeva", result.getNombreEps());
    }

    @Test
    void patchEps_inexistente() {
        when(epsRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EpsNoEncontradaException.class, () ->
                epsService.patchEps(99, eps));
    }
}
