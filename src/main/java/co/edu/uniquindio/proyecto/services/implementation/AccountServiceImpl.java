package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.ChangePasswordDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ProfileDTO;
import co.edu.uniquindio.proyecto.dto.TypeAccountDto;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.entity.Account;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.AccountService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@NoArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    protected ClientRepo clientRepo;
    @Autowired
    protected ModeratorRepo moderatorRepo;
    @Autowired
    protected MailService mailService;
    @Autowired
    protected JWTUtils jwtUtils;


    @Override
    public void updateProfile(ProfileDTO profileDTO) throws Exception{
        TypeAccountDto type = verifyAccountById(profileDTO.id());

        if(type.tipo().equals("CLIENTE")){
            Client client = (Client) type.account();
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) type.account();
            moderatorRepo.save(moderator);
        }
    }

    @Override
    public void forgotPassword(String email) throws Exception {
        TypeAccountDto type = verifyAccountByEmail(email);

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "PASSWORD");
        map.put("nickname", type.account().getNickname());
        map.put("id", type.account().getId());

        String token= jwtUtils.generatePasswordToken(type.account().getEmail(), map);
        mailService.sendMail(new EmailDTO(
                "Asistencia para cambiar contraseña",
                "<h1>Hola " + type.account().getName() + "</h1>\n" +
                        "        <p>Hemos recibido una solicitud para cambiar tu contraseña.</p>\n" +
                        "        <p>Por favor, sigue este enlace para cambiar tu contraseña: <a href=\"\">Cambiar Contraseña</a>.</p>\n" +
                        "        <p>Token generado tiempo limite un minuto: \""+token+"\"</p>\n" +
                        "        <p>Si no solicitaste este cambio, por favor ignora este mensaje.</p>\n" +
                        "        <p>¡Gracias por confiar en nosotros!</p>\n",
                email

        ));
    }

    @Override
    public void passwordRecovery(ChangePasswordDTO changePasswordDTO,@NotBlank(message = "Nesecita un token de autorización") String token) throws Exception {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        TypeAccountDto type = verifyAccountById((String)jws.getPayload().get("id"));

        if (changePasswordDTO.password().equals(changePasswordDTO.passwordConfirmation())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(changePasswordDTO.password());
            type.account().setPassword(password);
        }

        if(type.tipo().equals("CLIENTE")){
            Client client = (Client) type.account();
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) type.account();
            moderatorRepo.save(moderator);
        }
    }

    @Override
    public String deactivateAccount(String userId) throws Exception {

        //Obtenemos la cuenta que se quiere eliminar y le asignamos el estado inactivo
        TypeAccountDto type = verifyAccountById(userId);
        type.account().setState(StateRecord.INACTIVE);


        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que
        // actualiza el que ya existe
        if(type.tipo().equals("CLIENTE")){
            Client client = (Client) type.account();
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) type.account();
            moderatorRepo.save(moderator);
        }

        return type.account().getState().toString();
    }

    @Override
    public void logOutUser(String userId) throws Exception {
        //Buscamos el cliente
        TypeAccountDto type = verifyAccountById(userId);
        type.account().setLogin(StateRecord.INACTIVE);

        if(type.tipo().equals("CLIENTE")){
            Client client = (Client) type.account();
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) type.account();
            moderatorRepo.save(moderator);
        }
    }
    /**
     * Verificar si una cuenta existe .
     * @param accountId El nickname único del cliente que desea buscar.
     * @return La cuenta encontrada.
     */
    private TypeAccountDto verifyAccountById(String accountId) throws Exception {
        Optional<?> optionalAccount =clientRepo.findById(accountId);
        String tipo = "CLIENTE";

        if (optionalAccount.isEmpty()) {
            optionalAccount = moderatorRepo.findById(accountId);
            tipo= "MODERATOR";
        }
        //Si no se encontró el cliente o moderator, lanzamos una excepción
        if(optionalAccount.isEmpty())
            throw new Exception(
                    "No se encuentra una cuenta con el id= "+accountId);

        //retornamos la cuenta
        return new TypeAccountDto(tipo,(Account) optionalAccount.get() );
    }

    private TypeAccountDto verifyAccountByEmail(String accountEmail) throws Exception {
        Optional<?> optionalAccount =clientRepo.findByEmail(accountEmail);
        String tipo = "CLIENTE";

        if (optionalAccount.isEmpty()) {
            optionalAccount = moderatorRepo.findByEmail(accountEmail);
            tipo= "MODERATOR";
        }
        //Si no se encontró el cliente o moderator, lanzamos una excepción
        if(optionalAccount.isEmpty())
            throw new Exception(
                    "El correo: "+accountEmail+" no se encuentra registrado");


        //retornamos la cuenta
        return new TypeAccountDto(tipo,(Account) optionalAccount.get() );
    }

}
