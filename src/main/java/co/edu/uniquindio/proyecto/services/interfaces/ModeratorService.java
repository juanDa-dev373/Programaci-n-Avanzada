package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.dto.HistoryReviewDTO;
import co.edu.uniquindio.proyecto.dto.ReviewDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;

import java.util.List;

public interface ModeratorService extends AccountService {

    AccountDetailDTO getModeratorById(String moderator) throws Exception;

    /**
     * Verificar y aprobar un nuevo lugar creado por un usuario.
     *
     * @param reviewDTO El ID único del moderador que realiza la verificación,y
     *                   El ID único del lugar que se está verificando y aprobando.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de verificación y aprobación del lugar.
     */
    String verifyAndApproveBusiness(HistoryReviewDTO reviewDTO) throws Exception;

    /**
     * Rechazar un nuevo lugar creado por un usuario.
     *
     * @param reviewDTO El ID único del moderador que realiza el rechazo y
     *                    El ID único del lugar que se está rechazando.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de rechazo del lugar.
     */
    String rejectBusiness(HistoryReviewDTO reviewDTO) throws Exception;

    /**
     * Desactivar la cuenta de un usuario.
     *
     * @param moderatorId El ID único del moderador que desactiva la cuenta.
     * @param userId      El ID único del usuario cuya cuenta se va a desactivar.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso de desactivación de la cuenta.
     */
    String deactivateUserAccount(String token,String moderatorId, String userId) throws Exception;

    /**
     * Marcar un comentario de usuario como inapropiado.
     *
     * @param moderatorId El ID único del moderador que marca el comentario.
     * @param commentId   El ID único del comentario que se está marcando como inapropiado.
     * @return Un mensaje de confirmación de la acción.
     * @throws Exception Sí ocurre un error durante el proceso.
     */
    String markCommentAsInappropriate(String moderatorId, String commentId) throws Exception;

    /**
     * Devuelve la lista de reviews.
     *
     * @param moderatorId El ID único del moderador que marca el comentario.
     * @return Una lista del historial de revisiones.
     * @throws Exception Sí ocurre un error durante el proceso.
     */
    List<ReviewDTO> getListHistoryReviews(String moderatorId) throws Exception;
    /**
     * Devuelve la lista de negocios pendientes.
     *
     * @return Una lista negocios.
     * @throws Exception Sí ocurre un error durante el proceso.
     */
    List<Business> allBusinessPending() throws Exception;

}
