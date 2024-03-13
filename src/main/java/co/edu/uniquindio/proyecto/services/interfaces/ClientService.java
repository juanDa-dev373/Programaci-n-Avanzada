package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ClientDTO;
import co.edu.uniquindio.proyecto.dto.SingUpDTO;

public interface ClientService extends AccountService {

    void singClient(SingUpDTO sing);

    void getClient(ClientDTO Client);

    void removeCostumer();

    void deleteAccount();

}
