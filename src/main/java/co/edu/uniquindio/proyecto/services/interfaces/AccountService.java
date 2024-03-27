package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ProfileDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.SignUpDTO;

public interface AccountService {
    /**
     * Recuperar la contraseña de un cliente.
     */
    void passwordRecovery();

    /**
     * Registra un nuevo usuario en la plataforma.
     *
     * @param signUpDTO Los datos de registro del usuario.
     * @return Un mensaje de confirmación del registro.
     * @throws Exception Sí ocurre un error durante el proceso de registro.
     */
    String signUpUser(SignUpDTO signUpDTO) throws Exception;

    /**
     * Inicia sesión para un usuario existente.
     *
     * @param loginDTO Los datos de inicio de sesión del usuario.
     * @return Un token de autenticación para el usuario.
     * @throws Exception Si las credenciales son inválidas o si ocurre un error durante el proceso de inicio de sesión.
     */
    String logInUser(LoginDTO loginDTO) throws Exception;

    /**
     * Permite a un usuario recuperar su contraseña en caso de olvido.
     *
     * @param email El correo electrónico asociado a la cuenta del usuario.
     * @return Un mensaje de confirmación del proceso de recuperación de contraseña.
     * @throws Exception Si el correo electrónico no está asociado a ninguna cuenta registrada o si ocurre un error durante el proceso de recuperación.
     */
    String forgotPassword(String email) throws Exception;

    /**
     * Permite a un usuario ver y editar su información de perfil.
     *
     * @param profileDTO Los datos actualizados del perfil del usuario.
     * @throws Exception Sí ocurre un error durante el proceso de actualización del perfil.
     */
    void updateProfile(ProfileDTO profileDTO) throws Exception;

    /**
     * Permite a un usuario eliminar permanentemente su cuenta.
     *
     * @param userId El ID único del usuario que desea eliminar su cuenta.
     * @return Un mensaje de confirmación de la eliminación de la cuenta.
     * @throws Exception Sí ocurre un error durante el proceso de eliminación de la cuenta.
     */
    String deactivateAccount(String userId) throws Exception;

    /**
     * Cierra sesión para un usuario autenticado.
     *
     * @param userId El ID único del usuario que desea cerrar sesión.
     * @throws Exception Sí ocurre un error durante el proceso de cierre de sesión.
     */
    void logOutUser(String userId) throws Exception;



}
