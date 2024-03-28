package co.edu.uniquindio.proyecto.dto;

import java.time.LocalDateTime;

public record EventDTO(

        String id,
        String description,
        LocalDateTime date,
        String title,
        String client,
        String business
) {
}
