package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import jakarta.validation.constraints.NotBlank;

public record ReviewDTO( @NotBlank StateBusiness stateBusiness,
                         @NotBlank(message = "Es necesario que ingrese el id del moderador") String moderatorId,
                         @NotBlank(message = "Es necesario que ingrese el id del negocio") String idBusiness) {
}
