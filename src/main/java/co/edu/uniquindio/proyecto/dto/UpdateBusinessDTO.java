package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateBusinessDTO(
        @NotBlank String id
) {
}
