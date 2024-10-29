package co.edu.uniquindio.proyecto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBusiness {

    private String id;
    private String listName;
    private List<Map<String,String>> idBusiness;

}
