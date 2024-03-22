package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ListDTO(
        @NotBlank String id,
        @NotBlank String name
) {
}
