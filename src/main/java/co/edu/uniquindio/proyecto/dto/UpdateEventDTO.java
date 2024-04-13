package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UpdateEventDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese la descripcion") String description,
        @NotBlank(message = "Es necesario que ingrese el horario local") LocalDateTime date,
        @NotBlank(message = "Es necesario que ingrese un titulo") String title,
        @NotBlank(message = "Es necesario que ingrese el cliente") String client,
        @NotBlank(message = "Es necesario que ingrese el negocio") String business
) {
}
