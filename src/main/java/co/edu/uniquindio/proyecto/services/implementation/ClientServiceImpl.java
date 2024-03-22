package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl extends AccountServiceImpl implements ClientService {

   private final ClientRepo clientRepo;

    public ClientServiceImpl(ClientRepo clientRepo) {
        super(clientRepo);
        this.clientRepo = clientRepo;
    }

    @Override
    public String singClient(SingUpDTO sing) throws Exception {

     if( existEmail(sing.email()) ){
      throw new Exception("El correo ya se encuentra registrado");
     }
     if( existNickname(sing.nickname()) ){
      throw new Exception("El nickname ya se encuentra registrado por otro usuario");
     }
     //Se crea el objeto Cliente
     Client cliente = new Client();

//Se le asignan sus campos
     cliente.setName(sing.name() );
     cliente.setNickname( sing.nickname() );
     cliente.setCity( sing.city() );
     cliente.setProfilePhoto( sing.photo() );

     cliente.setEmail( sing.email() );
     cliente.setPassword( sing.password() );
     cliente.setState(StateRecord.ACTIVE);
     cliente.setListClient(new ArrayList<ListBusiness>());
     cliente.getListClient().add(
             new ListBusiness("01", "Favorites", new ArrayList<String>())
     );

//Se guarda en la base de datos y obtenemos el objeto registrado
     Client clientSave = clientRepo.save(cliente);

//Retornamos el id (código) del cliente registrado
      return clientSave.getId();
    }

    @Override
    public List<ListBusiness> getListBusiness(String idClient) throws Exception {
        Client client= getClientById(idClient);
        List<ListBusiness> listBusinesses = client.getListClient();
        //Si no se encontró el cliente, lanzamos una excepción
        if(listBusinesses.isEmpty()){
           throw new Exception("No cuenta con listas de negocios");
        }
        return listBusinesses;
    }


    private boolean existEmail(String email) {
     return clientRepo.findByEmail(email).isPresent();
 }


    private boolean existNickname(String nickname) {
        return clientRepo.findByNickname(nickname).isPresent();
 }

    @Override
    public Client getClientById(String idClient) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Client> optionalClient = clientRepo.findById( idClient );

//Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

//Obtenemos el cliente que se quiere actualizar y le asignamos los
// nuevos valores (el nickname no se puede cambiar)
        return optionalClient.get();
    }

    @Override
    public Client getClientByNickname(String nickname) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Client> optionalClient = clientRepo.findByNickname( nickname );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

        //Obtenemos el cliente que se quiere actualizar y le asignamos los
        // nuevos valores (el nickname no se puede cambiar)
        return optionalClient.get();
    }

    @Override
    public boolean removeClient(String idClient) throws Exception {
        //Buscamos el cliente que se quiere eliminar
        Optional<Client> optionalCliente = clientRepo.findById( idClient );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalCliente.isEmpty()){
            throw new Exception("No se encontró el cliente a eliminar");
        }

        //Obtenemos el cliente que se quiere eliminar y le asignamos el estado inactivo
        Client cliente = optionalCliente.get();
        cliente.setState(StateRecord.INACTIVE);

        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que
        // actualiza el que ya existe
        clientRepo.save(cliente);
        return true;
    }

    @Override
    public List<ItemClientDTO> listClient() {
        //Obtenemos todos los clientes de la base de datos
        List<Client> clientes = clientRepo.findAll();

        //Creamos una lista de DTOs de clientes
        List<ItemClientDTO> items = new ArrayList<>();

        //Recorremos la lista de clientes y por cada uno creamos un DTO y lo agregamos a la lista
        for (Client client : clientes) {
            items.add(new ItemClientDTO(client.getId(), client.getName(),
                    client.getProfilePhoto(), client.getNickname(), client.getCity()));
        }

        return items;
    }
}
