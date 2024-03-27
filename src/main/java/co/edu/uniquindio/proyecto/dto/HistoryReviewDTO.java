package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record HistoryReviewDTO
        (String description,
         @NotBlank String idModerator,
         @NotBlank String idBusiness) { }
