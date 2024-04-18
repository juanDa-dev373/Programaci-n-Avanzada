package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateBusinessDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank String idCliente,
        @NotBlank(message = "Es necesario que ingrese el nombre") String name,
        @NotBlank(message = "Es necesario que ingrese la descripcion") String description,
        @NotNull Location location,
        @NotNull List<String>images,
        @NotNull TypeBusiness typeBusiness,
        List<Schedule> timeSchedules,
        List<String> phone,
        HistoryReview review
) {
}
