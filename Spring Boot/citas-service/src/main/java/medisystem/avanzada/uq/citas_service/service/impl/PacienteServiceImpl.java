package medisystem.avanzada.uq.citas_service.service.impl;

import medisystem.avanzada.uq.citas_service.mappers.PacienteMapper;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.paciente.PacienteResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.*;
import medisystem.avanzada.uq.citas_service.exceptions.PacienteNoEncontradoException;
import medisystem.avanzada.uq.citas_service.repositories.*;
import medisystem.avanzada.uq.citas_service.service.PacienteService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dbPacienteService")
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final EpsRepository epsRepository;
    private final UsuarioRepository usuarioRepository;
    private final TelefonoRepository telefonoRepository;
    private final PacienteMapper pacienteMapper; // <- se inyecta

    public PacienteServiceImpl(PacienteRepository pacienteRepository,
                               EpsRepository epsRepository,
                               UsuarioRepository usuarioRepository,
                               TelefonoRepository telefonoRepository,
                               PacienteMapper pacienteMapper) { // <- agregado
        this.pacienteRepository = pacienteRepository;
        this.epsRepository = epsRepository;
        this.usuarioRepository = usuarioRepository;
        this.telefonoRepository = telefonoRepository;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    public List<Paciente> getPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente getPacienteById(String idPaciente) {
        return pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
    }

    @Override
    public Paciente postPaciente(Paciente paciente) {
        if (paciente.getIdPaciente() == null || paciente.getIdPaciente().isBlank()) {
            paciente.setIdPaciente(UUID.randomUUID().toString());
        }
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente putPaciente(String idPaciente, Paciente paciente) {
        return pacienteRepository.findById(idPaciente)
                .map(p -> {
                    p.setNombrePaciente(paciente.getNombrePaciente());
                    p.setCiudad(paciente.getCiudad());
                    p.setCorreo(paciente.getCorreo());
                    p.setEps(paciente.getEps());
                    return pacienteRepository.save(p);
                })
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
    }

    @Override
    public void deletePaciente(String idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));
        pacienteRepository.delete(paciente);
    }

    @Override
    public Paciente patchPaciente(String idPaciente, Paciente paciente) {
        Paciente existente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoException(
                        "Paciente con id " + idPaciente + " no encontrado"
                ));

        if (paciente.getNombrePaciente() != null) {
            existente.setNombrePaciente(paciente.getNombrePaciente());
        }
        if (paciente.getCiudad() != null) {
            existente.setCiudad(paciente.getCiudad());
        }
        if (paciente.getCorreo() != null) {
            existente.setCorreo(paciente.getCorreo());
        }
        if (paciente.getEps() != null) {
            existente.setEps(paciente.getEps());
        }

        return pacienteRepository.save(existente);
    }

    // ========================================================
    // NUEVO MÉTODO USANDO DTOs (Entrada + Salida)
    // ========================================================
    @Override
    public PacienteResponseDTO registrarPaciente(PacienteRequestDTO dto) {
        // Crear usuario asociado
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());
        usuarioRepository.save(usuario);

        // Buscar EPS
        Eps eps = epsRepository.findById(dto.getIdEps())
                .orElseThrow(() -> new RuntimeException("EPS no encontrada con ID: " + dto.getIdEps()));

        // Crear entidad Paciente usando mapper inyectado
        Paciente paciente = pacienteMapper.toEntity(dto, eps, usuario);
        paciente.setIdPaciente(UUID.randomUUID().toString());
        pacienteRepository.save(paciente);

        // Guardar teléfonos
        Set<String> telefonos = new HashSet<>();
        if (dto.getTelefonos() != null) {
            for (String numero : dto.getTelefonos()) {
                Telefono telefono = new Telefono();
                telefono.setTelefono(numero);
                telefonoRepository.save(telefono);
                telefonos.add(numero);
            }
        }

        // Retornar DTO de salida
        return pacienteMapper.toDTO(paciente, telefonos);
    }
}
