package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ListBusinessOwnerDTO(
        @NotBlank String idClient
) {
}
