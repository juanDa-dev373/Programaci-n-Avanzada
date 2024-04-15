package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.services.interfaces.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Test
    public void loginModeratorSuccessTest() throws Exception {
        LoginDTO login= new LoginDTO("moderator@email.com","password");
        TokenDTO tokenDTO=authenticationService.loginModerator(login);
        //Se verifica que se cambió el estado login a "ACTIVE"
        Assertions.assertNotNull(tokenDTO);
    }
    @Test
    public void loginClienteSuccessTest() throws Exception {
        LoginDTO login= new LoginDTO("davidcort24@gmail.com","1234");
        TokenDTO tokenDTO=authenticationService.loginClient(login);
        //Se verifica que se cambió el estado login a "ACTIVE"
        Assertions.assertNotNull(tokenDTO);
    }
}
