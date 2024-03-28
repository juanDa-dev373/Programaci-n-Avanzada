package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;

import java.util.List;

public record BusinessDetailDTO(
        String id,
        String name,
        String description,
        String idClient,
        Location location,
        List<String>images,
        TypeBusiness typeBusiness

) {
}
