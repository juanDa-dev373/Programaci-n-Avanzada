package co.edu.uniquindio.proyecto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record ValidacionDTO(
        String campo,
        String error
) {
}
