package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BusinessDto(
        @NotBlank String id,
        String name,
        String description,
        Location location,
        List<String> images,
        TypeBusiness typeBusiness,
        boolean open
) {
}
