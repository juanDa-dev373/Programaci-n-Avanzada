package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateBusinessDTO(
        @NotBlank String id,
        @NotBlank String idCliente,
        String name,
        String description,
        Location location,
        List<String>images,
        TypeBusiness typeBusiness,
        List<Schedule> timeSchedules,
        List<String> phone,
        HistoryReview review
) {
}
