package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateCommentDTO(
        @NotBlank(message = "Es necesario que ingrese el id ") String id,
        @NotBlank(message = "Es necesario que ingrese la fecha local") LocalDateTime date,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idClient,
        @NotBlank(message = "Es necesario que ingrese el id del negocio") String idBusiness,
        @NotBlank(message = "Es necesario que ingrese un mensaje") String message
) {
}
