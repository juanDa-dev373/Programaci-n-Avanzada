package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;

import java.util.List;

public interface ClientService extends AccountService {

    String singClient(SingUpDTO sing) throws Exception;

    Client getClientById(String idClient) throws Exception;

    Client getClientByNickname(String nickname) throws Exception;

    List<ListBusiness> getListBusiness(String idClient) throws Exception;

    boolean removeClient(String idClient)throws Exception;

    List<ItemClientDTO> listClient();

}
