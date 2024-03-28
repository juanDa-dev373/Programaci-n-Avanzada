package co.edu.uniquindio.proyecto.model.entity;

import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String name;
    private String password;
    private String nickname;
    private String email;
    private StateRecord state;
    private StateRecord login;

}
