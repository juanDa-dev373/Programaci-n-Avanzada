package co.edu.uniquindio.proyecto.dto;

import java.time.LocalDateTime;

public record ResponseCommentDTO(
        String idComment,
        String idResponse,
        LocalDateTime date,
        String idClient,
        String idBusiness,
        String message
) {
}
