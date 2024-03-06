package co.edu.uniquindio.proyecto.model.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Document("clientes")
public class Cliente implements Serializable{
    private String fotoPerfil;
    @Id
    private String codigo;
    private String nickName;
    private String ciudad;

}
