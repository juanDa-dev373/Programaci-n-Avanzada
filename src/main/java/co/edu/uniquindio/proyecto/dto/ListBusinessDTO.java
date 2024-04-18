package co.edu.uniquindio.proyecto.dto;

import java.util.List;

public record ListBusinessDTO(
        String id,
        String listName,
        List<BusinessDto> businesses
) {
}
