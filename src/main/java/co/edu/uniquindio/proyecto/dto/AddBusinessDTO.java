package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddBusinessDTO(

        @NotBlank @Size(max = 50, min = 1, message="el nombre debe estar entre 1 y 50 letras") String name,
        @NotBlank @Size(max = 400,min = 20, message = "debe contener por lo menos 20 letras y maximo 400") String description,
        @NotBlank(message = "Es necesario que ingrese el id del cliente ") String idClient,
        @NotNull Location location,
        @NotNull List<String>images,
        @NotNull TypeBusiness typeBusiness,
        @NotNull List<Schedule> timeSchedules,
        @NotNull List<String> phone,
        HistoryReview review
) {
}
