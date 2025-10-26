package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Telefono;
import medisystem.avanzada.uq.citas_service.exceptions.TelefonoNoEncontradoException;
import medisystem.avanzada.uq.citas_service.mappers.TelefonoMapper; // NECESARIO para DTOs
import medisystem.avanzada.uq.citas_service.repositories.TelefonoRepository;
import medisystem.avanzada.uq.citas_service.services.TelefonoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbTelefonoService")
@Transactional
public class TelefonoServiceImpl implements TelefonoService {

    private final TelefonoRepository telefonoRepository;
    private final TelefonoMapper telefonoMapper;

    // Inyección por Constructor
    public TelefonoServiceImpl(TelefonoRepository telefonoRepository, TelefonoMapper telefonoMapper) {
        this.telefonoRepository = telefonoRepository;
        this.telefonoMapper = telefonoMapper;
    }

    // --------------------------------------------------------------------
    // MÉTODOS DE NEGOCIO (Uso interno por PacienteService)
    // --------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public Telefono findOrCreateByNumero(String numero) {
        // Usa el método findByTelefono que añadiste al Repositorio
        return telefonoRepository.findByTelefono(numero)
                .orElseGet(() -> {
                    // Si no existe, crea y guarda el nuevo teléfono
                    Telefono nuevoTelefono = new Telefono();
                    nuevoTelefono.setTelefono(numero);
                    return telefonoRepository.save(nuevoTelefono);
                });
    }

    // --------------------------------------------------------------------
    // MÉTODOS CRUD ESTANDARIZADOS (Uso por el Controller)
    // --------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<TelefonoResponseDTO> getAllTelefonos() {
        // Retorna DTOs usando el stream y el mapper
        return telefonoRepository.findAll().stream()
                .map(telefonoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TelefonoResponseDTO getTelefonoById(Long idTelefono) { // ID es LONG
        Telefono telefono = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(idTelefono)); // Usa constructor (Long)

        return telefonoMapper.toResponseDTO(telefono);
    }

    @Override
    public TelefonoResponseDTO postTelefono(TelefonoRequestDTO dto) { // Acepta DTO
        // 1. Convertir DTO a Entidad
        Telefono telefono = telefonoMapper.toEntity(dto);

        // 2. Guardar
        Telefono guardado = telefonoRepository.save(telefono);

        // 3. Convertir Entidad a Response DTO
        return telefonoMapper.toResponseDTO(guardado);
    }

    @Override
    public TelefonoResponseDTO putTelefono(Long idTelefono, TelefonoRequestDTO dto) { // ID es LONG y acepta DTO
        // Buscar o lanzar 404
        Telefono existente = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(idTelefono));

        // Actualizar: MapStruct o manualmente (si no usas MapStruct para updates)
        if (dto.getTelefono() != null) {
            existente.setTelefono(dto.getTelefono());
        }

        return telefonoMapper.toResponseDTO(telefonoRepository.save(existente));
    }

    @Override
    public void deleteTelefono(Long idTelefono) { // ID es LONG
        // Usamos existsById para validar y lanzar 404 si no existe
        if (!telefonoRepository.existsById(idTelefono)) {
            throw new TelefonoNoEncontradoException(idTelefono);
        }
        telefonoRepository.deleteById(idTelefono);
    }

    @Override
    public TelefonoResponseDTO patchTelefono(Long idTelefono, TelefonoRequestDTO dto) { // ID es LONG y acepta DTO
        // Buscar o lanzar 404
        Telefono existente = telefonoRepository.findById(idTelefono)
                .orElseThrow(() -> new TelefonoNoEncontradoException(idTelefono));

        // Actualización parcial
        if (dto.getTelefono() != null) {
            existente.setTelefono(dto.getTelefono());
        }

        return telefonoMapper.toResponseDTO(telefonoRepository.save(existente));
    }
}