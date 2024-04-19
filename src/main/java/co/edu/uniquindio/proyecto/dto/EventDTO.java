package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventDTO(

        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese la descripción") String description,
        @NotNull LocalDateTime date,
        @NotBlank(message = "Es necesario que ingrese el titulo") String title,
        @NotBlank(message = "Es necesario que ingrese el cliente") String client,
        @NotBlank(message = "Es necesario que ingrese el negocio") String business
) {
}
