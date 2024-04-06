package co.edu.uniquindio.proyecto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private double longituded;
    private double latitude;
}
