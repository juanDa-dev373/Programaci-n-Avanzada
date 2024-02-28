package org.example.proyecto;

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
    @Id
    private String codigo;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

}
