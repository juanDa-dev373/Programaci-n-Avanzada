package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("business")
public class Business {

    @Id
    private String id;
    private String name;
    private String description;
    private String idClient;
    private Location location;
    private List<String> images;
    private TypeBusiness typeBusiness;
    private List<Schedule> timeSchedules;
    private List<String> phone;
    private HistoryReview review;
    private StateBusiness stateBusiness;
    private StateRecord state;
    private boolean open;
}
