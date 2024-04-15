package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.awt.*;

public record LocationDTO(
        @NotBlank(message = "Es necesario que ingrese la ubicacion") @GeoSpatialIndexed Location location,
        @NotBlank(message = "Es necesario que ingrese la distancia maxima") double maxDistance
) {
}
