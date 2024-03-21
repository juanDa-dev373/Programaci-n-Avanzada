package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SingUpDTO(
        @NotBlank String name,
        @NotBlank String nickname,

        @NotBlank String email,

        @NotBlank @Pattern(regexp = "") String password,
        @NotBlank String photo,
        @NotBlank String city


        ) {
}
