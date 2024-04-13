package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record CalificationDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idCliente,
        @NotBlank(message = "Es necesario que ingrese el id de Business") String idBusiness,
        @NotBlank(message = "Es necesario que ingrese la calificacion") int calification
) {
}
