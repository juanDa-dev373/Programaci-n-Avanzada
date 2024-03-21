package co.edu.uniquindio.proyecto.model.documents;
import co.edu.uniquindio.proyecto.model.entity.Account;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("moderator")
public class Moderator extends Account {
    @Id
    private String id;
    private List<HistoryReview> historyReview;
}
