package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.services.interfaces.AuthenticationService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ClientService clientService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, ClientService clientService) {
        this.authenticationService = authenticationService;
        this.clientService = clientService;
    }


    @PostMapping("/login-client")
    public ResponseEntity<MensajeDTO<TokenDTO>> loginClient(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = authenticationService.loginClient(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }

    @PostMapping("/login-moderator")
    public ResponseEntity<MensajeDTO<TokenDTO>> loginModerator(@Valid @RequestBody
                                                      LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = authenticationService.loginModerator(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }

    @PostMapping("/singUp-client")
    public ResponseEntity<MensajeDTO<String>> signUpClient(@Valid @RequestBody
                                                               SignUpDTO signUpDTO) throws Exception {
        clientService.signUpUser(signUpDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cliente registrado correctamente"));
    }


    @PostMapping("/passwordRecovery/{token}")
    public ResponseEntity<MensajeDTO<String>> passwordRecovery(@RequestBody ChangePasswordDTO changePasswordDTO,@Valid @PathVariable String token) throws Exception {
        clientService.passwordRecovery(changePasswordDTO,token);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Recuperación de contraseña exitosa"));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<MensajeDTO<String>> forgotPassword(@RequestParam("email") String email) throws Exception {
        clientService.forgotPassword(email);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Solicitud de recuperación de contraseña enviada"));
    }

}