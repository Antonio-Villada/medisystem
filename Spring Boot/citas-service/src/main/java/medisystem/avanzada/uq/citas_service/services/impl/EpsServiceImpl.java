package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.eps.EpsRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.eps.EpsResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Eps;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.EpsYaExisteException; // Excepción necesaria
import medisystem.avanzada.uq.citas_service.mappers.EpsMapper;
import medisystem.avanzada.uq.citas_service.repositories.EpsRepository;
import medisystem.avanzada.uq.citas_service.services.EpsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbEpsService")
@Transactional
public class EpsServiceImpl implements EpsService {

    private final EpsRepository epsRepository;
    private final EpsMapper epsMapper; // Inyección del Mapper

    public EpsServiceImpl(EpsRepository epsRepository, EpsMapper epsMapper) {
        this.epsRepository = epsRepository;
        this.epsMapper = epsMapper;
    }

    // ========================================================
    // LECTURA (GET) - Ahora devuelve DTOs
    // ========================================================

    @Override
    @Transactional(readOnly = true)
    public List<EpsResponseDTO> getAllEps() {
        return epsRepository.findAll().stream()
                .map(epsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    // CORREGIDO: int -> Long
    public EpsResponseDTO getEpsById(Long idEps) {
        Eps eps = epsRepository.findById(idEps)
                // Uso del constructor con Long
                .orElseThrow(() -> new EpsNoEncontradaException(idEps));

        return epsMapper.toResponseDTO(eps);
    }

    // ========================================================
    // CREACIÓN (POST) - Ahora usa DTOs y validación
    // ========================================================

    @Override
    public EpsResponseDTO postEps(EpsRequestDTO dto) {
        // Validación de Negocio: No debe existir otra con el mismo nombre
        if (epsRepository.existsByNombreEps(dto.getNombreEps())) {
            throw new EpsYaExisteException(dto.getNombreEps(), true);
        }

        Eps entity = epsMapper.toEntity(dto);
        Eps saved = epsRepository.save(entity);
        return epsMapper.toResponseDTO(saved);
    }

    // ========================================================
    // ACTUALIZACIÓN TOTAL (PUT) - Ahora usa DTOs y validación
    // ========================================================

    @Override
    // CORREGIDO: int -> Long, Eps -> EpsRequestDTO
    public EpsResponseDTO putEps(Long idEps, EpsRequestDTO dto) {
        Eps existente = epsRepository.findById(idEps)
                .orElseThrow(() -> new EpsNoEncontradaException(idEps));

        // Validación de Negocio: Si el nombre cambia, debe ser único
        if (!existente.getNombreEps().equalsIgnoreCase(dto.getNombreEps())) {
            if (epsRepository.existsByNombreEps(dto.getNombreEps())) {
                throw new EpsYaExisteException(dto.getNombreEps(), true);
            }
        }

        existente.setNombreEps(dto.getNombreEps());

        Eps actualizado = epsRepository.save(existente);
        return epsMapper.toResponseDTO(actualizado);
    }

    // ========================================================
    // ACTUALIZACIÓN PARCIAL (PATCH) - Ahora usa DTOs
    // ========================================================

    @Override
    // CORREGIDO: int -> Long, Eps -> EpsRequestDTO
    public EpsResponseDTO patchEps(Long idEps, EpsRequestDTO dto) {
        Eps existente = epsRepository.findById(idEps)
                .orElseThrow(() -> new EpsNoEncontradaException(idEps));

        if (dto.getNombreEps() != null) {
            // Validación de Negocio: Si el nombre cambia, debe ser único
            if (!existente.getNombreEps().equalsIgnoreCase(dto.getNombreEps())) {
                if (epsRepository.existsByNombreEps(dto.getNombreEps())) {
                    throw new EpsYaExisteException(dto.getNombreEps(), true);
                }
            }
            existente.setNombreEps(dto.getNombreEps());
        }

        Eps actualizado = epsRepository.save(existente);
        return epsMapper.toResponseDTO(actualizado);
    }

    // ========================================================
    // ELIMINACIÓN (DELETE)
    // ========================================================

    @Override
    // CORREGIDO: int -> Long
    public void deleteEps(Long idEps) {
        // Usamos existsById para validar antes de borrar
        if (!epsRepository.existsById(idEps)) {
            throw new EpsNoEncontradaException(idEps);
        }
        epsRepository.deleteById(idEps);
    }
}