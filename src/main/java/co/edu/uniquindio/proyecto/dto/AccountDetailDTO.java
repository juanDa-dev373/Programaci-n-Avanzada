package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountDetailDTO(
        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String nickname,
        @NotBlank @Email String email,
        @NotBlank StateRecord login
        ) {

}
