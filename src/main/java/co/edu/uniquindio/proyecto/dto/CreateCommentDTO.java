package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateCommentDTO(
        @NotBlank String id,
        @NotBlank LocalDateTime date,
        @NotBlank String idClient,
        @NotBlank String idBusiness,
        @NotBlank String message
) {
}
