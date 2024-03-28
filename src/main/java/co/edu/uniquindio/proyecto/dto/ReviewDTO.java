package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import jakarta.validation.constraints.NotBlank;

public record ReviewDTO(@NotBlank StateBusiness stateBusiness,
                        @NotBlank String moderatorId,
                        @NotBlank String idBusiness) {
}
