package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SingUpDTO(
        @NotBlank @Length(max = 100)  String name,
        @NotBlank String nickname,

        @NotBlank @Email String email,

        @NotBlank @Length(max = 20, min = 8) @Pattern(regexp = "") String password,
        @NotBlank String photo,
        @NotBlank String city


        ) {
}
