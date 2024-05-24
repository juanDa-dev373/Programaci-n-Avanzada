package co.edu.uniquindio.proyecto.model.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("event")
public class Event {
    private String id;
    private String description;
    private String date;
    private String title;
    private String client;
    private String business;
}
