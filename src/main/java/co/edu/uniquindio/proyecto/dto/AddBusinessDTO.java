package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBusinessDTO(
        @NotBlank String id
) {
}
