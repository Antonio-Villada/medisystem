package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Telefono; // Necesario para el uso interno

import java.util.List;

public interface TelefonoService {


    Telefono findOrCreateByNumero(String numero);
    List<TelefonoResponseDTO> getAllTelefonos();
    TelefonoResponseDTO getTelefonoById(Long idTelefono); // ID estandarizado a Long
    TelefonoResponseDTO postTelefono(TelefonoRequestDTO dto);
    TelefonoResponseDTO putTelefono(Long idTelefono, TelefonoRequestDTO dto);
    void deleteTelefono(Long idTelefono); // ID estandarizado a Long
    TelefonoResponseDTO patchTelefono(Long idTelefono, TelefonoRequestDTO dto);
}