// auth.models.ts

/**
 * Interface que define la estructura del cuerpo (body) de la petición de Login.
 * Coincide con tu LoginRequestDTO.
 */
export interface LoginRequest {
  username: string;
  password: string;
}

/**
 * Interface que define la estructura de la respuesta exitosa del servidor después del Login.
 * Coincide con tu LoginResponseDTO.
 */
export interface LoginResponse {
   // CORRECCIÓN: El backend envía "jwtToken"[cite: 70], no "token".
  jwtToken: string;

  // Opcional: El backend también envía 'type', pero no es necesario para el login.
  // type?: "Bearer";

  // NOTA: Los campos id, nombre y roles no vienen en tu LoginResponseDTO[cite: 70].
  // Esos datos se piden por separado al endpoint /usuarios/perfil[cite: 79].
}
