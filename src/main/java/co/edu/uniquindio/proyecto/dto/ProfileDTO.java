package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfileDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el nombre") String name,
        @NotBlank(message = "Es necesario que ingrese la foto de perfil") String profilePhoto,
        @NotBlank(message = "Es necesario que ingrese la ciudad") String city
) {
}
