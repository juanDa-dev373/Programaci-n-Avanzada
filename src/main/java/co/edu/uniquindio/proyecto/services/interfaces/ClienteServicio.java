package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.EditProfileDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.SingUpCunstomerDTO;

public interface ClienteServicio extends CuentaServicio{

    void singupCostumer(SingUpCunstomerDTO singUpCunstomerDTO) throws Exception;

    void editProfile(EditProfileDTO editProfileDto) throws Exception;

    void getCostumer(String nickname) throws Exception;

    void removeCostumer(String nickName) throws Exception;

    void deleteAccount( String nickName) throws Exception;

    void login(LoginDTO loginDTO) throws Exception;

    void passwordRecovery(String email) throws Exception;
    void Comment() throws Exception;
    void Qualify() throws Exception;
    void FavoriteList() throws Exception;

}
