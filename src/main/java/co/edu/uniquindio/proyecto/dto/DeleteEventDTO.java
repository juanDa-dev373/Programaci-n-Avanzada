package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteEventDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el id del negocio") String idBusiness,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idClient
) {
}
