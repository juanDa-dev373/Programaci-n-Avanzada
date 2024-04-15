package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import jakarta.validation.constraints.NotBlank;

public record RegistrerReviewDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        HistoryReview review
) {

}
