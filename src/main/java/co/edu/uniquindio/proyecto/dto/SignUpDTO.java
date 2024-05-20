package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SignUpDTO(
        @NotBlank @Length(max = 100)  String name,
        @NotBlank(message = "Es necesario que ingrese el nickname") String nickname,

        @NotBlank(message = "Es necesario que ingrese el email") @Email String email,

        @NotBlank @Length(max = 20, min = 8,message = "La contraseña de tener una longitud en un rango entre 8 y 20")
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$",
                message = "Recuerde que la contraseña debe tener al menos un carácter especial, una letra mayúscula, una minúscula y un numero.   "
        )
        String password,
        @NotBlank(message = "Es necesario que ingrese la foto") String photo,
        @NotBlank(message = "Es necesario que ingrese la ciudad") String city


        ) {
}
