package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ImagenDTO(
        @NotBlank String id,
        String url
) {
}
