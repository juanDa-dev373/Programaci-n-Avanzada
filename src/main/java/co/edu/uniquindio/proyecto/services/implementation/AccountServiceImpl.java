package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.EditProfileDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.entity.Account;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.AccountService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final ClientRepo clientRepo;
    private final ModeratorRepo moderatorRepo;

    public AccountServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
        moderatorRepo=null;
    }
    public AccountServiceImpl(ModeratorRepo moderatorRepo) {
        this.moderatorRepo = moderatorRepo;
        clientRepo=null;
    }

    public AccountServiceImpl() {
        moderatorRepo = null;
        clientRepo=null;
    }




    @Override
    public String editProfile(EditProfileDTO edit) {
        if(clientRepo!=null){
            Optional<Client> acount=clientRepo.findById(edit.id());

        }else{
            Optional<Moderator> acount=moderatorRepo.findById(edit.id());
        }


        return "si";
    }

    @Override
    public String login(LoginDTO login) {
        return "";
    }

    @Override
    public void passwordRecovery() {

    }
}
