package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordDTO(
        @NotBlank
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$")
        @NotBlank(                message = "Recuerde que la contraseña debe tener al menos un carácter especial, una letra mayúscula, una minúscula y un numero.   "
        ) String password,
        @NotBlank
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$")
        @NotBlank(message = "Es necesario que ingrese la contraseña") String passwordConfirmation
        )
{
}
