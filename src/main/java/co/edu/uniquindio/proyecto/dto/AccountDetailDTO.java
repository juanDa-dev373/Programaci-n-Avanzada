package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountDetailDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank String name,
        @NotBlank(message = "Es necesario que ingrese el nickname") String nickname,
        @NotBlank @Email(message = "Es necesario que ingrese el email") String email,
        @NotBlank StateRecord login
        ) {

}
