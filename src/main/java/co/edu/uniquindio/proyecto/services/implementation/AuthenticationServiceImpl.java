package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.AuthenticationService;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientRepo clientRepo;
    private final ModeratorRepo moderatorRepo;
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO loginClient(LoginDTO loginDTO) throws Exception {
        Optional<Client> clienteOptional = clientRepo.findByEmail(loginDTO.email());

        if (clienteOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Client client = clienteOptional.get();

        if( !passwordEncoder.matches(loginDTO.password(), client.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", client.getName());
        map.put("id", client.getId());

        return new TokenDTO( jwtUtils.generateToken(client.getEmail(), map) );
    }

    @Override
    public TokenDTO loginModerator(LoginDTO loginDTO) throws Exception {
        Optional<Moderator> moderatorOptional = moderatorRepo.findByEmail(loginDTO.email());

        if (moderatorOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Moderator moderator = moderatorOptional.get();

        if( !passwordEncoder.matches(loginDTO.password(), moderator.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", moderator.getName());
        map.put("id", moderator.getId());

        return new TokenDTO( jwtUtils.generateToken(moderator.getEmail(), map) );
    }
}
