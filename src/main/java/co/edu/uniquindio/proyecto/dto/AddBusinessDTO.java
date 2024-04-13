package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.util.List;

public record AddBusinessDTO(
        @NotBlank String id,
        @NotBlank @Min(1) @Max(50) String name,
        @NotBlank @Max(400) @Min(20) String description,
        @NotBlank(message = "Es necesario que ingrese el id del cliente ") String idClient,
        @NotBlank Location location,
        @NotBlank List<String>images,
        @NotBlank TypeBusiness typeBusiness,
        @NotBlank List<Schedule> timeSchedules,
        @NotBlank List<String> phone,
        HistoryReview review
) {
}
