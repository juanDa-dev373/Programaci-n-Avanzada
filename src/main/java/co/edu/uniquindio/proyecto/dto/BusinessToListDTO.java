package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record BusinessToListDTO(
        @NotBlank String clientId,
        @NotBlank String listId,
        @NotBlank String businessId
) {
}
