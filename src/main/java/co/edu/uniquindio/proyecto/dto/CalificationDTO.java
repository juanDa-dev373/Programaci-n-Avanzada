package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CalificationDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idCliente,
        @NotBlank(message = "Es necesario que ingrese el id de Business") String idBusiness,
        @NotNull(message = "Es necesario que ingrese la calificacion") int calification
) {
}
