package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ProfileDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.entity.Account;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.AccountService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    protected final ClientRepo clientRepo;

    protected final ModeratorRepo moderatorRepo;

    public AccountServiceImpl() {
        this.clientRepo = null;
        this.moderatorRepo = null;
    }
    public AccountServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
        this.moderatorRepo = null;
    }
    public AccountServiceImpl(ModeratorRepo moderatorRepo) {
        this.clientRepo = null;
        this.moderatorRepo = moderatorRepo;
    }

    @Override
    public void updateProfile(ProfileDTO profileDTO) throws Exception{
    }

    @Override
    public void passwordRecovery(String email) {

    }

    @Override
    public String deactivateAccount(String userId) throws Exception {

        //Obtenemos la cuenta que se quiere eliminar y le asignamos el estado inactivo
        Account account = verifyAccountById(userId);
        account.setState(StateRecord.INACTIVE);


        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que
        // actualiza el que ya existe
        if(clientRepo!=null){
            Client client = (Client) account;
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) account;
            moderatorRepo.save(moderator);
        }

        return account.getState().toString();
    }

    @Override
    public void logOutUser(String userId) throws Exception {
        //Buscamos el cliente
        Account account = verifyAccountById(userId);
        account.setLogin(StateRecord.INACTIVE);

        if(clientRepo!=null){
            Client client = (Client) account;
            clientRepo.save(client);
        }else{
            Moderator moderator = (Moderator) account;
            moderatorRepo.save(moderator);
        }
    }
    /**
     * Verificar si una cuenta existe .
     * @param accountId El nickname único del cliente que desea buscar.
     * @return La cuenta encontrada.
     */
    private Account verifyAccountById(String accountId) throws Exception {
        Optional<?> optionalAccount = (clientRepo!=null) ?
                clientRepo.findById(accountId) : moderatorRepo.findById(accountId);

        //Si no se encontró el cliente o moderator, lanzamos una excepción
        if(optionalAccount.isEmpty())
            throw new Exception(
                    "{message:"+ "\"No se encuentra una cuenta con el id= "+accountId+"\n ,"+ "statusCode: Error }");

        //retornamos la cuenta
        return (Account) optionalAccount.get();
    }

    @Override
    public void forgotPassword(String email) throws Exception {
    }

}
