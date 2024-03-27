package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.awt.*;

public record LocationDTO(
        @GeoSpatialIndexed Location location,
        double maxDistance
) {
}
