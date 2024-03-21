package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.ItemClientDTO;
import co.edu.uniquindio.proyecto.dto.SingUpDTO;
import co.edu.uniquindio.proyecto.dto.ClientDTO;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl extends AccountServiceImpl implements ClientService {

   private final ClientRepo clienteRepo;

    public ClientServiceImpl(ClientRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
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

//Se guarda en la base de datos y obtenemos el objeto registrado
     Client clientSave = clienteRepo.save(cliente);

//Retornamos el id (código) del cliente registrado
      return clientSave.getId();
    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void listBusiness() {

    }


    private boolean existEmail(String email) {
     return clienteRepo.findByEmail(email).isPresent();
 }


    private boolean existNickname(String nickname) {
        return clienteRepo.findByNickname(nickname).isPresent();
 }

    @Override
    public void updateClient(Client client) throws Exception {

    }

    @Override
    public Client getClientById(String idClient) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Client> optionalClient = clienteRepo.findById( idClient );

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
        Optional<Client> optionalClient = clienteRepo.findByNickname( nickname );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

        //Obtenemos el cliente que se quiere actualizar y le asignamos los
        // nuevos valores (el nickname no se puede cambiar)
        return optionalClient.get();
    }

    @Override
    public void removeClient(String nickname) throws Exception {
        //Buscamos el cliente que se quiere eliminar
        Optional<Client> optionalCliente = clienteRepo.findByNickname( nickname );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalCliente.isEmpty()){
            throw new Exception("No se encontró el cliente a eliminar");
        }

        //Obtenemos el cliente que se quiere eliminar y le asignamos el estado inactivo
        Client cliente = optionalCliente.get();
        cliente.setState(StateRecord.INACTIVE);

        //Como el objeto cliente ya tiene un id, el save() no crea un nuevo registro sino que
        // actualiza el que ya existe
        clienteRepo.save(cliente);
    }

    @Override
    public List<ItemClientDTO> listClient() {
        //Obtenemos todos los clientes de la base de datos
        List<Client> clientes = clienteRepo.findAll();

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
