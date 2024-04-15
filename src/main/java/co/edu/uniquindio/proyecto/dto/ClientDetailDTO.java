package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientDetailDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el nombre") String name,
        @NotBlank(message = "Es necesario que ingrese el nickname") String nickname,
        @NotBlank(message = "Es necesario que ingrese el email") @Email String email,
        @NotBlank StateRecord login,
        @NotBlank(message = "Es necesario que ingrese la ciudad") String city,
        @NotBlank(message = "Es necesario que ingrese la foto de perfil") String profilePhoto

) {
}
