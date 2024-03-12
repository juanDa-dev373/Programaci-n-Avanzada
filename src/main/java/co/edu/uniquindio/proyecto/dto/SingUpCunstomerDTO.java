package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SingUpCunstomerDTO(
        @NotBlank @Length (max = 100) String nombre,
        @NotBlank String fotoPerfil,
        @NotBlank String nickName,
        @NotBlank @Email String email,
        @NotBlank @Length(max = 20, min = 8) String password,
        @NotBlank String placeRecidence
) {
}
