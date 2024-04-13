package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;

public interface AuthenticationService {
    /**
     * Inicia sesión para un usuario existente.
     *
     * @param loginDTO Los datos de inicio de sesión del usuario.
     * @return Un token de autenticación para el usuario.
     * @throws Exception Si las credenciales son inválidas o si ocurre un error durante el proceso de inicio de sesión.
     */
    TokenDTO loginClient(LoginDTO loginDTO) throws Exception;

    /**
     * Inicia sesión para un usuario existente.
     *
     * @param loginDTO Los datos de inicio de sesión del usuario.
     * @return Un token de autenticación para el usuario.
     * @throws Exception Si las credenciales son inválidas o si ocurre un error durante el proceso de inicio de sesión.
     */
    TokenDTO loginModerator(LoginDTO loginDTO) throws Exception;
}
