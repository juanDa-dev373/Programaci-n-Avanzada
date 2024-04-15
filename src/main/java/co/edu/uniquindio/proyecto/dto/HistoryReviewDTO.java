package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record HistoryReviewDTO
        (String description,
         @NotBlank(message = "Es necesario que ingrese el id del moderador") String idModerator,
         @NotBlank(message = "Es necesario que ingrese el id del negocio") String idBusiness) { }
