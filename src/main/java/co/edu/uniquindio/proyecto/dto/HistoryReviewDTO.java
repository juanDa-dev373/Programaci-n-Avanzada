package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record HistoryReviewDTO
        (@NotNull String description,
         @NotBlank String idModerator,
         @NotBlank String idBusiness) { }
