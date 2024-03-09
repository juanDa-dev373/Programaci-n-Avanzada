package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ChangePasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;

public interface CuentaServicio {
    void login(LoginDTO loginDTO) throws Exception;
    void deleteAccount(String IdAccount) throws Exception;
    void sendRecoveryLink(String email) throws  Exception;
    void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;
}
