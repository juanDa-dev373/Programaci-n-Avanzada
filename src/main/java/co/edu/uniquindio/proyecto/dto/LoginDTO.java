package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Es necesario que ingrese el email")
        @Email String email,
        @NotBlank(message = "Es necesario que ingrese la contraseña")
        String password
) {
}
