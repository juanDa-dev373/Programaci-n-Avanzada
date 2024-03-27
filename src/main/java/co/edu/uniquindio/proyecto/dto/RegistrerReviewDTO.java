package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;

public record RegistrerReviewDTO(
        String id,
        HistoryReview review
) {

}
