package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;

public interface ModeratorService extends AccountService {
    AccountDetailDTO getModeratorById(String moderator) throws Exception;

    /**
     * Verificar y aprobar un nuevo lugar creado por un usuario.
     *
     * @param moderatorId El ID único del moderador que realiza la verificación.
     * @param placeId     El ID único del lugar que se está verificando y aprobando.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de verificación y aprobación del lugar.
     */
    String verifyAndApproveBusiness(String moderatorId, String placeId) throws Exception;

    /**
     * Rechazar un nuevo lugar creado por un usuario.
     *
     * @param moderatorId El ID único del moderador que realiza el rechazo.
     * @param placeId     El ID único del lugar que se está rechazando.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de rechazo del lugar.
     */
    String rejectBusiness(String moderatorId, String placeId) throws Exception;

    /**
     * Desactivar la cuenta de un usuario.
     *
     * @param moderatorId El ID único del moderador que desactiva la cuenta.
     * @param userId      El ID único del usuario cuya cuenta se va a desactivar.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de desactivación de la cuenta.
     */
    String deactivateUserAccount(String moderatorId, String userId) throws Exception;

    /**
     * Activar la cuenta de un usuario previamente desactivada.
     *
     * @param moderatorId El ID único del moderador que activa la cuenta.
     * @param userId      El ID único del usuario cuya cuenta se va a activar.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de activación de la cuenta.
     */
    String activateUserAccount(String moderatorId, String userId) throws Exception;


    /**
     * Marcar un comentario de usuario como inapropiado.
     *
     * @param moderatorId El ID único del moderador que marca el comentario.
     * @param commentId   El ID único del comentario que se está marcando como inapropiado.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de marcado del comentario.
     */
    String markCommentAsInappropriate(String moderatorId, String commentId) throws Exception;

}
