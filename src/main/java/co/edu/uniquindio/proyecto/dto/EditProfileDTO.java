package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record EditProfileDTO(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String profilePhoto,
        @NotBlank String city
) {
}
