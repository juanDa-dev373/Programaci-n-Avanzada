package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordDTO(
        @NotBlank
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$")
        @NotBlank(message = "Es necesario que ingrese la contraseña") String password,
        @NotBlank
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$")
        @NotBlank(message = "Es necesario que ingrese la contraseña") String passwordConfirmation,
        @NotBlank(message = "Es necesario que ingrese el email") @Email String email
        ) {
}
