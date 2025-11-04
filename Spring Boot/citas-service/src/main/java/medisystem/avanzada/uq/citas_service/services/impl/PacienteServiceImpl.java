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


    @Override
    public PacienteResponseDTO registrarPaciente(PacienteRequestDTO dto) {

        if (pacienteRepository.existsById(dto.getIdPaciente())) {
            throw new PacienteYaExisteException(dto.getIdPaciente());
        }

        Usuario usuario = userService.crearNuevoUsuario(
                dto.getUsername(),
                dto.getPassword(),
                RolNombre.PACIENTE
        );

        Eps eps = epsRepository.findById(dto.getIdEps())
                // CORRECCIÓN: Pasar solo el Long (ID)
                .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));

        Paciente paciente = pacienteMapper.toEntity(dto, eps, usuario);
        paciente.setIdPaciente(dto.getIdPaciente());
        Paciente savedPaciente = pacienteRepository.save(paciente);

        Set<String> numerosGuardados = saveTelefonos(savedPaciente, dto.getTelefonos());

        return pacienteMapper.toResponseDTO(savedPaciente, numerosGuardados);
    }


    @Override
    public PacienteResponseDTO putPaciente(String idPaciente, PacienteRequestDTO dto) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        if (!existente.getIdPaciente().equals(dto.getIdPaciente())) {
            throw new IllegalArgumentException("No se permite cambiar la identificación del paciente.");
        }

        existente.setNombrePaciente(dto.getNombrePaciente());
        existente.setCiudad(dto.getCiudad());
        existente.setCorreo(dto.getCorreo());
        Eps nuevaEps = epsRepository.findById(dto.getIdEps())
                .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));
        existente.setEps(nuevaEps);
        Set<String> numerosGuardados = updateTelefonos(existente, dto.getTelefonos());
        Paciente updatedPaciente = pacienteRepository.save(existente);

        return pacienteMapper.toResponseDTO(updatedPaciente, numerosGuardados);
    }


    @Override
    public PacienteResponseDTO patchPaciente(String idPaciente, PacienteRequestDTO dto) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        boolean cambiosEnTelefono = false;

        if (dto.getNombrePaciente() != null) {
            existente.setNombrePaciente(dto.getNombrePaciente());
        }
        if (dto.getCiudad() != null) {
            existente.setCiudad(dto.getCiudad());
        }
        if (dto.getCorreo() != null) {
            existente.setCorreo(dto.getCorreo());
        }

        if (dto.getIdEps() != null) {
            Eps nuevaEps = epsRepository.findById(dto.getIdEps())
                    .orElseThrow(() -> new EpsNoEncontradaException(dto.getIdEps()));
            existente.setEps(nuevaEps);
        }

        if (dto.getTelefonos() != null) {
            updateTelefonos(existente, dto.getTelefonos());
            cambiosEnTelefono = true;
        }

        Paciente updatedPaciente = pacienteRepository.save(existente);

        Set<String> telefonos;
        if (cambiosEnTelefono) {
            telefonos = dto.getTelefonos();
        } else {
            telefonos = updatedPaciente.getTelefonos().stream()
                    .map(pt -> pt.getTelefono().getTelefono())
                    .collect(Collectors.toSet());
        }

        return pacienteMapper.toResponseDTO(updatedPaciente, telefonos);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO getPacientePorUsername(String username) {
        Paciente paciente = pacienteRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new PacienteNoEncontradoException("No se encontró paciente para el username: " + username));

        return mapPacienteToResponseDTO(paciente);
    }


    @Override
    public void deletePaciente(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(idPaciente));

        pacienteRepository.delete(paciente);
    }

    private PacienteResponseDTO mapPacienteToResponseDTO(Paciente paciente) {
        Set<String> telefonos = paciente.getTelefonos().stream()
                .map(pt -> pt.getTelefono().getTelefono())
                .collect(Collectors.toSet());
        return pacienteMapper.toResponseDTO(paciente, telefonos);
    }

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

    private Set<String> updateTelefonos(Paciente paciente, Set<String> nuevosNumeros) {
        pacienteTelefonoRepository.deleteAllByPaciente(paciente);

        return saveTelefonos(paciente, nuevosNumeros);
    }
}