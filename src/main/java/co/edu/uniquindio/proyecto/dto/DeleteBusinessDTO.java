package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteBusinessDTO(
        @NotBlank String idBusiness,
        @NotBlank String idClient
) {
}
