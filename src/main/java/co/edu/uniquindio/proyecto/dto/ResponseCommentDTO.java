package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ResponseCommentDTO(
        @NotBlank(message = "Es necesario que ingrese el id del comentario") String idComment,
        @NotBlank(message = "Es necesario que ingrese la ubicacion") String idResponse,
        LocalDateTime date,
        @NotBlank(message = "Es necesario que ingrese la ubicacion") String idClient,
        @NotBlank(message = "Es necesario que ingrese el id del negocio")String idBusiness,
        @NotBlank(message = "Es necesario que ingrese un mensaje") String message
) {
}
