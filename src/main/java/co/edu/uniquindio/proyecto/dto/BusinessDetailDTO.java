package co.edu.uniquindio.proyecto.dto;

import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record BusinessDetailDTO(
        @NotBlank(message = "Es necesario que ingrese el id") String id,
        @NotBlank(message = "Es necesario que ingrese el nombre") String name,
        @NotBlank(message = "Es necesario que ingrese la descripcion") String description,
        @NotBlank(message = "Es necesario que ingrese el id del cliente") String idClient,
        @NotBlank(message = "Es necesario que ingrese la ubicacion") Location location,
        List<MultipartFile>images,
        TypeBusiness typeBusiness

) {
}
