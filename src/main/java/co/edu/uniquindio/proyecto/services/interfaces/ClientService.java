package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ClientDTO;
import co.edu.uniquindio.proyecto.dto.ItemClientDTO;
import co.edu.uniquindio.proyecto.dto.SingUpDTO;
import co.edu.uniquindio.proyecto.model.documents.Client;

import java.util.List;

public interface ClientService extends AccountService {

    String singClient(SingUpDTO sing) throws Exception;

    Client getClientById(String idClient) throws Exception;

    Client getClientByNickname(String nickname) throws Exception;

    void deleteAccount();

    void listBusiness();

    void updateClient(Client cliente)throws Exception;

    void removeClient(String idCuenta)throws Exception;

    List<ItemClientDTO> listClient();

}
