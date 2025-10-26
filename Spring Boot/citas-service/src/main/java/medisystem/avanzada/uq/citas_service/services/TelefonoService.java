package medisystem.avanzada.uq.citas_service.services;

import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoRequestDTO;
import medisystem.avanzada.uq.citas_service.dtos.telefono.TelefonoResponseDTO;
import medisystem.avanzada.uq.citas_service.entities.Telefono; // Necesario para el uso interno

import java.util.List;

public interface TelefonoService {

    // --------------------------------------------------------------------
    // MÉTODOS DE NEGOCIO (Uso interno por PacienteService)
    // --------------------------------------------------------------------

    /**
     * Busca la entidad Telefono por su número o la crea si no existe.
     * Devuelve la entidad Telefono para que el PacienteService pueda manejar la relación.
     * @param numero El número de teléfono.
     * @return La entidad Telefono existente o recién creada.
     */
    Telefono findOrCreateByNumero(String numero);

    // --------------------------------------------------------------------
    // MÉTODOS CRUD ESTANDARIZADOS (Uso por el Controller)
    // --------------------------------------------------------------------

    /**
     * Devuelve todos los teléfonos como DTOs.
     */
    List<TelefonoResponseDTO> getAllTelefonos();

    /**
     * Busca un teléfono por su ID.
     */
    TelefonoResponseDTO getTelefonoById(Long idTelefono); // ID estandarizado a Long

    /**
     * Crea un nuevo teléfono.
     */
    TelefonoResponseDTO postTelefono(TelefonoRequestDTO dto);

    /**
     * Reemplaza completamente los datos de un teléfono.
     */
    TelefonoResponseDTO putTelefono(Long idTelefono, TelefonoRequestDTO dto);

    /**
     * Elimina un teléfono por ID.
     */
    void deleteTelefono(Long idTelefono); // ID estandarizado a Long

    /**
     * Actualiza parcialmente los datos de un teléfono.
     */
    TelefonoResponseDTO patchTelefono(Long idTelefono, TelefonoRequestDTO dto);
}