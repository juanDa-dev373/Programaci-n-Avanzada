package co.edu.uniquindio.proyecto.dto;

public record DeleteCommentDTO(
        String id,
        String idCliente,
        String business,
        String idClientOwnerBusiness
) {
}
