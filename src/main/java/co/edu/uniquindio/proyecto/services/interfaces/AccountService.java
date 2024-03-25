package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.EditProfileDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;

public interface AccountService {

    String editProfile(EditProfileDTO edit) throws Exception;

    String login(LoginDTO login) throws Exception;

    void passwordRecovery();


}
