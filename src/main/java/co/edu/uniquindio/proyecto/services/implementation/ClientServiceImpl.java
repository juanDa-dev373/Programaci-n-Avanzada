package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.SingUpDTO;
import co.edu.uniquindio.proyecto.dto.ClientDTO;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends AccountServiceImpl implements ClientService {

   private final ClientRepo clienteRepo;

    public ClientServiceImpl(ClientRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @Override
    public void singClient(SingUpDTO sing) {

    }

    @Override
    public void getClient(ClientDTO Client) {

    }

    @Override
    public void removeCostumer() {

    }

    @Override
    public void deleteAccount() {

    }
}
