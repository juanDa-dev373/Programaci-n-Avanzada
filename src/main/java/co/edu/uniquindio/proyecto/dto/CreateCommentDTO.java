package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCommentDTO(
        String id,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idClient,
        @NotBlank(message = "Es necesario que ingrese el id del negocio") String idBusiness,
        @NotBlank(message = "Es necesario que ingrese un mensaje") String message
) {
}
