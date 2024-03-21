package co.edu.uniquindio.proyecto.model.documents;

import co.edu.uniquindio.proyecto.model.entity.Account;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("client")
public class Client extends Account implements Serializable{

    @Id
    private String id;
    private List<ListBusiness> listClient;
    private String profilePhoto;
    private String city;


}
