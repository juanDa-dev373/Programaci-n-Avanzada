package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.SignUpDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.services.interfaces.AuthenticationService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService authenticationService;
    ClientService clientService;

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
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.signUpUser(signUpDTO)));
    }

}