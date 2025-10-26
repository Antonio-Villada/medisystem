package medisystem.avanzada.uq.citas_service.services.impl;

import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.*;
import medisystem.avanzada.uq.citas_service.entities.RolNombre;
import medisystem.avanzada.uq.citas_service.exceptions.EpsNoEncontradaException;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteNoEncontradoException;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteYaExisteException;
import medisystem.avanzada.uq.citas_service.mappers.PacienteMapper;
import medisystem.avanzada.uq.citas_service.repositories.*;
import medisystem.avanzada.uq.citas_service.services.PacienteService;
import medisystem.avanzada.uq.citas_service.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final EpsRepository epsRepository;
    private final TelefonoRepository telefonoRepository;
    private final PacienteTelefonoRepository pacienteTelefonoRepository;
    private final UserService userService;
    private final PacienteMapper pacienteMapper;

    // Constructor limpio con todas las dependencias
    public PacienteServiceImpl(PacienteRepository pacienteRepository,
                               EpsRepository epsRepository,
                               TelefonoRepository telefonoRepository,
                               PacienteTelefonoRepository pacienteTelefonoRepository,
                               UserService userService,
                               PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.epsRepository = epsRepository;
        this.telefonoRepository = telefonoRepository;
        this.pacienteTelefonoRepository = pacienteTelefonoRepository;
        this.userService = userService;
        this.pacienteMapper = pacienteMapper;
    }

    // ========================================================
    // LECTURA (GET)
    // ========================================================

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> getAllPacientes() {
        return pacienteRepository.findAll().stream()
                .map(this::mapPacienteToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO getPacienteById(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));
        return mapPacienteToResponseDTO(paciente);
    }

    // ========================================================
    // CREACIÓN (POST) - IMPLEMENTA registrarPaciente
    // ========================================================

    @Override
    public PacienteResponseDTO registrarPaciente(PacienteRequestDTO dto) {

        // Validación: El paciente (por cédula/ID) no debe existir
        if (pacienteRepository.existsById(dto.getIdPaciente())) {
            throw new PacienteYaExisteException(dto.getIdPaciente());
        }

        // 1. Crear/Validar Usuario (Delegado al UserService, incluye validación de username)
        Usuario usuario = userService.crearNuevoUsuario(
                dto.getUsername(),
                dto.getPassword(),
                RolNombre.PACIENTE
        );

        // 2. Buscar EPS
        Eps eps = epsRepository.findById(dto.getIdEps())
                // CORRECCIÓN: Pasar solo el Long (ID)
                .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));

        // 3. Crear Entidad Paciente
        Paciente paciente = pacienteMapper.toEntity(dto, eps, usuario);
        paciente.setIdPaciente(dto.getIdPaciente());
        Paciente savedPaciente = pacienteRepository.save(paciente);

        // 4. Manejar y Guardar Teléfonos (Lógica compleja)
        Set<String> numerosGuardados = saveTelefonos(savedPaciente, dto.getTelefonos());

        // 5. Retornar DTO de salida
        return pacienteMapper.toResponseDTO(savedPaciente, numerosGuardados);
    }

    // ========================================================
    // ACTUALIZACIÓN TOTAL (PUT)
    // ========================================================

    @Override
    public PacienteResponseDTO putPaciente(String idPaciente, PacienteRequestDTO dto) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        // El ID del paciente no debe cambiar
        if (!existente.getIdPaciente().equals(dto.getIdPaciente())) {
            throw new IllegalArgumentException("No se permite cambiar la identificación del paciente.");
        }

        // 1. Actualizar campos simples
        existente.setNombrePaciente(dto.getNombrePaciente());
        existente.setCiudad(dto.getCiudad());
        existente.setCorreo(dto.getCorreo());

        // 2. Actualizar EPS
        Eps nuevaEps = epsRepository.findById(dto.getIdEps())
                // CORRECCIÓN: Pasar solo el Long (ID)
                .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));
        existente.setEps(nuevaEps);

        // 3. ACTUALIZACIÓN DE TELÉFONOS (Borrar y recrear)
        Set<String> numerosGuardados = updateTelefonos(existente, dto.getTelefonos());

        Paciente updatedPaciente = pacienteRepository.save(existente);

        // 4. Retornar DTO
        return pacienteMapper.toResponseDTO(updatedPaciente, numerosGuardados);
    }

    // ========================================================
    // ACTUALIZACIÓN PARCIAL (PATCH)
    // ========================================================

    @Override
    public PacienteResponseDTO patchPaciente(String idPaciente, PacienteRequestDTO dto) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        boolean cambiosEnTelefono = false;

        // 1. Actualización condicional de campos simples
        if (dto.getNombrePaciente() != null) {
            existente.setNombrePaciente(dto.getNombrePaciente());
        }
        if (dto.getCiudad() != null) {
            existente.setCiudad(dto.getCiudad());
        }
        if (dto.getCorreo() != null) {
            existente.setCorreo(dto.getCorreo());
        }

        // 2. Actualizar EPS si el ID se proporciona
        if (dto.getIdEps() != null) {
            Eps nuevaEps = epsRepository.findById(dto.getIdEps())
                    // CORRECCIÓN: Pasar solo el Long (ID)
                    .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));
            existente.setEps(nuevaEps);
        }

        // 3. ACTUALIZACIÓN DE TELÉFONOS (Si se proveen, se BORRAN los antiguos y se crean los nuevos)
        if (dto.getTelefonos() != null) {
            updateTelefonos(existente, dto.getTelefonos());
            cambiosEnTelefono = true;
        }

        Paciente updatedPaciente = pacienteRepository.save(existente);

        // 4. Retornar DTO
        Set<String> telefonos;
        if (cambiosEnTelefono) {
            // Si hubo cambio, se usa la lista recién guardada del DTO
            telefonos = dto.getTelefonos();
        } else {
            // Si no hubo cambio, se extrae la lista que ya tenía la entidad
            telefonos = updatedPaciente.getTelefonos().stream()
                    .map(pt -> pt.getTelefono().getTelefono())
                    .collect(Collectors.toSet());
        }

        return pacienteMapper.toResponseDTO(updatedPaciente, telefonos);
    }

    // ========================================================
    // ELIMINACIÓN (DELETE)
    // ========================================================

    @Override
    public void deletePaciente(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        pacienteRepository.delete(paciente);
    }

    // ========================================================
    // MÉTODOS PRIVADOS DE AYUDA (Lógica de Teléfonos)
    // ========================================================

    /** Convierte la entidad Paciente a DTO, resolviendo la lista de teléfonos. */
    private PacienteResponseDTO mapPacienteToResponseDTO(Paciente paciente) {
        Set<String> telefonos = paciente.getTelefonos().stream()
                .map(pt -> pt.getTelefono().getTelefono())
                .collect(Collectors.toSet());
        return pacienteMapper.toResponseDTO(paciente, telefonos);
    }

    /** Lógica para guardar las relaciones de teléfono (usada en POST). */
    private Set<String> saveTelefonos(Paciente paciente, Set<String> numeros) {
        if (numeros == null) return Set.of();
        Set<String> numerosGuardados = new HashSet<>();

        for (String numero : numeros) {
            // Busca o crea la entidad Telefono
            Telefono telefonoEntity = telefonoRepository.findByTelefono(numero)
                    .orElseGet(() -> {
                        Telefono nuevoTelefono = new Telefono();
                        nuevoTelefono.setTelefono(numero);
                        return telefonoRepository.save(nuevoTelefono);
                    });

            // Crea la entidad de unión PacienteTelefono
            PacienteTelefono pt = new PacienteTelefono();
            pt.setPaciente(paciente);
            pt.setTelefono(telefonoEntity);
            pacienteTelefonoRepository.save(pt);

            numerosGuardados.add(numero);
        }
        return numerosGuardados;
    }

    /** Lógica para actualizar las relaciones de teléfono (usada en PUT/PATCH). */
    private Set<String> updateTelefonos(Paciente paciente, Set<String> nuevosNumeros) {
        // 1. Borrar vínculos antiguos
        pacienteTelefonoRepository.deleteAllByPaciente(paciente);

        // 2. Guardar los nuevos (usa la lógica de saveTelefonos)
        return saveTelefonos(paciente, nuevosNumeros);
    }
}