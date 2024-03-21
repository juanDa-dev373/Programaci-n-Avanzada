package co.edu.uniquindio.proyecto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBusiness {
    private String listName;
    private List<String> codesBusiness;

}
