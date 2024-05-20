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
import org.springframework.beans.factory.annotation.Qualifier;
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
    public void updateProfile(ProfileDTO profileDTO, String token) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        String idToken = (String) jws.getPayload().get("id");

        if (!idToken.equals(profileDTO.id()))
            throw new Exception(
                    "No cuenta con los permisos suficientes"
            );

        TypeAccountDto type = verifyAccountById(profileDTO.id());

        type.account().setName(profileDTO.name());
        Moderator moderator = (Moderator) type.account();
        moderatorRepo.save(moderator);

        mailService.sendMail(new EmailDTO(
                        "Cuenta Actualizada Exitosamente",
                        "      <h1>Felicitaciones por Crear su Cuenta Exitosamente</h1>\n" +
                                "        <p>¡Gracias por unirse a nuestra plataforma!</p>\n" +
                                "        <p>Su cuenta ha sido creada exitosamente.</p>\n" ,
                        type.account().getEmail()
                )
        );
    }

    @Override
    public void forgotPassword(String email) throws Exception {
        TypeAccountDto type = verifyAccountByEmail(email);

        Map<String, Object> map = new HashMap<>();
        map.put("rol", "PASSWORD");
        map.put("rolAccount", type.tipo());
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
        if(!jws.getPayload().get("rol").equals("PASSWORD"))
            throw new Exception(
                    "Token no valido");
        if (changePasswordDTO.password().equals(changePasswordDTO.passwordConfirmation())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(changePasswordDTO.password());
            type.account().setPassword(password);
        }

        if(jws.getPayload().get("rolAccount").equals("CLIENTE")){
            Client client = (Client) type.account();
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) type.account();
            moderatorRepo.save(moderator);
        }
    }

    @Override
    public String deactivateAccount(String token,String userId) throws Exception {

        Jws<Claims> jws = jwtUtils.parseJwt(token);
        TypeAccountDto peticion = verifyAccountById((String)jws.getPayload().get("id"));
        //Obtenemos la cuenta que se quiere eliminar y le asignamos el estado inactivo
        if(peticion.tipo().equals("CLIENTE")){
            if (!peticion.account().getId().equals(userId)){
                throw new Exception(
                        "No tiene los permisos sufientes");
            }
        }
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
    public void logOutUser(String token) throws Exception {
        //Buscamos el cliente
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        TypeAccountDto type = verifyAccountById((String)jws.getPayload().get("id"));
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
