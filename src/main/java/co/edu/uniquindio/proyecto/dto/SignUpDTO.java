package co.edu.uniquindio.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record SignUpDTO(
        @NotBlank @Length(max = 100)  String name,
        @NotBlank(message = "Es necesario que ingrese el nickname") String nickname,

        @NotBlank(message = "Es necesario que ingrese el email") @Email String email,

        @NotBlank @Length(max = 20, min = 8)
        @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$")
        String password,
        @NotBlank(message = "Es necesario que ingrese la foto") String urlPhoto,
        @NotBlank(message = "Es necesario que ingrese la ciudad") String city


        ) {
}
