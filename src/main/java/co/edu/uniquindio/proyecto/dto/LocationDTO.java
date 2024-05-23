package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.awt.*;

public record LocationDTO(
        @NotNull @GeoSpatialIndexed Location location,
        @NotNull double maxDistance,
        String search
) {
}
