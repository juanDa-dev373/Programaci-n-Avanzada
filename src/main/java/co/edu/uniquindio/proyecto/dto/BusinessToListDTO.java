package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record BusinessToListDTO(
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String clientId,
        @NotBlank(message = "Es necesario que ingrese la lista de id") String listId,
        @NotBlank String businessId
) {
}
