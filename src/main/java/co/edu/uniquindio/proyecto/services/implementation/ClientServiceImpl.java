package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends AccountServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final BusinessService businessService;
    private final MailService mailService;
    private final ImageService imageService;

    @Override
    public String signUpUser(SignUpDTO sing) throws Exception {

     if( existEmail(sing.email()) )
      throw new Exception("El correo ya se encuentra registrado");

     if( existNickname(sing.nickname()) )
      throw new Exception("El nickname ya se encuentra registrado por otro usuario");

     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     String password = passwordEncoder.encode(sing.password());


     //Se crea el objeto Cliente
     Client cliente = new Client();

    //Se le asignan sus campos
     cliente.setName(sing.name() );
     cliente.setNickname( sing.nickname() );
     cliente.setCity( sing.city() );
     cliente.setProfilePhoto((String)imageService.saveImage(sing.photo()).get("url"));

     cliente.setEmail( sing.email() );
     cliente.setPassword(password);
     cliente.setLogin(StateRecord.ACTIVE);
     cliente.setState(StateRecord.ACTIVE);
     cliente.setListClient(new ArrayList<>());
     cliente.getListClient().add(
             new ListBusiness("01", "Favorites", new ArrayList<>())
     );

    //Se guarda en la base de datos y obtenemos el objeto registrado
     Client clientSave = clientRepo.save(cliente);
     mailService.sendMail(new EmailDTO(
             "Cuenta Creada Exitosamente",
             "      <h1>Felicitaciones por Crear su Cuenta Exitosamente</h1>\n" +
                     "        <p>¡Gracias por unirse a nuestra plataforma!</p>\n" +
                     "        <p>Su cuenta ha sido creada exitosamente.</p>\n" ,
                    sing.email()
             )

     );

    //Retornamos el ID (código) del cliente registrado
      return clientSave.getId();
    }

    @Override
    public void updateProfile(ProfileDTO profileDTO) throws Exception{
        //Se obtiene la cuenta con el ID
        Optional<Client> optionalClient = clientRepo.findById(profileDTO.id()) ;

        //Si no se encontró la cuenta, lanzamos una excepción
        if(optionalClient.isEmpty())
            throw new Exception(
                    "{message:"+ "\"No se encuentra una cuenta con el id= "+profileDTO.id()+"\n ,"+ "statusCode: Error }"
            );

        Client client = optionalClient.get();
        client.setName(profileDTO.name());
        client.setCity(profileDTO.city());
        client.setProfilePhoto((String)imageService.saveImage(profileDTO.profilePhoto()).get("url"));

        clientRepo.save(client);
    }

    @Override
    public ListBusinessDTO getListBusiness(String idClient, String nameList) throws Exception {
        Optional<Client> optionalClient = clientRepo.findById( idClient );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

        Client client = optionalClient.get();
        List<BusinessDto> business= new ArrayList<>();
        ListBusinessDTO listBusinessDto=null;
        for(ListBusiness list: client.getListClient()){
            if(list.getListName().equals(nameList)){

                for (String idBusiness: list.getIdBusiness()){
                    Business b= businessService.search(idBusiness);
                    business.add(new BusinessDto(
                            b.getId(),
                            b.getName(),
                            b.getDescription(),
                            b.getLocation(),
                            b.getImages(),
                            b.getTypeBusiness()
                    ));
                }
                listBusinessDto=new ListBusinessDTO(list.getId(),list.getListName(),business);
            }
        }
        if (listBusinessDto==null){
            throw new Exception("La lista de negocios no existe");
        }
        return listBusinessDto;
    }
    @Override
    public List<ListBusiness> getListsBusinesses(String idClient) throws Exception {
        Optional<Client> optionalClient = clientRepo.findById( idClient );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

        Client client = optionalClient.get();


        return client.getListClient();
    }


    private boolean existEmail(String email) {
     return clientRepo.findByEmail(email).isPresent();
 }


    private boolean existNickname(String nickname) {
        return clientRepo.findByNickname(nickname).isPresent();
 }

    @Override
    public AccountDetailDTO getClientById(String idClient) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Client> optionalClient = clientRepo.findById( idClient );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception(
                    "{message:"+ "\"No se encontró el cliente a actualizar\","+
                            "statusCode: Error }"
            );
        }
        Client client = optionalClient.get();
        //Obtenemos el cliente que se quiere actualizar y le asignamos los
        // nuevos valores (el nickname no se puede cambiar)
        return new AccountDetailDTO(
                client.getId(),
                client.getName(),
                client.getNickname(),
                client.getEmail(),
                client.getLogin()
        );
    }


    @Override
    public List<ItemClientDTO> listClient() {
        //Obtenemos todos los clientes de la base de datos
        List<Client> clients = clientRepo.findAll();

        //Creamos una lista de DTO de clientes
        List<ItemClientDTO> items = new ArrayList<>();

        //Recorremos la lista de clientes y por cada uno creamos un DTO y lo agregamos a la lista
        for (Client client : clients) {
            items.add(new ItemClientDTO(client.getId(), client.getName(),
                    client.getProfilePhoto(), client.getNickname(), client.getCity()));
        }

        return items;
    }

    @Override
    public ListBusiness createBusinessList(String clientId, String listName)throws Exception {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "{message:"+ "\"No se encuentra una cuenta con el id= "+clientId+"\"\n ,"+ "statusCode: Error }"));

        ListBusiness listBusiness = new ListBusiness();
        listBusiness.setListName(listName);
        listBusiness.setId((listClient().size()+1)+"");
        listBusiness.setIdBusiness(new ArrayList<>());
        if(listBusinessExist(clientId,listName))
            throw new Exception( "{message:"+ "\"El cliente ya tiene una lista con este nombre= "+listName+"\"\n ,"+ "statusCode: Error }");

        client.getListClient().add(listBusiness);

        clientRepo.save(client);

        return listBusiness;
    }
    /**
     * Obtener la ruta y dirección para llegar a un lugar deseado.
     * @param idClient El ID único del cliente que desea buscar.
     * @param listName El nombre único de la lista de negocios crear.
     * @return True si existe en el cliente.
     */
    private boolean listBusinessExist(String idClient,String listName) {
        Client client = clientRepo.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException(
                        "{message:"+ "\"No se encuentra una cuenta con el id= "+idClient+"\"\n ,"+ "statusCode: Error }"));

        for (ListBusiness listBusiness : client.getListClient()) {
            if (listBusiness.getListName().equals(listName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteBusinessList(String clientId, String listName) throws IllegalArgumentException{
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "{message:"+ "\"No se encuentra una cuenta con el id= "+clientId+"\"\n ,"+ "statusCode: Error }"));

        ListBusiness listToRemove = null;
        for (ListBusiness listBusiness : client.getListClient()) {
            if (listBusiness.getListName().equals(listName)) {
                listToRemove = listBusiness;
                break;
            }
        }

        if (listToRemove == null) throw new IllegalArgumentException(
                "{message:"+ "\"No se encuentra una lista con el id= "+listName+"\"\n ,"+ "statusCode: Error }");

        client.getListClient().remove(listToRemove);
        clientRepo.save(client);

    }

    @Override
    public void addBusinessToList(BusinessToListDTO addBusiness) throws IllegalArgumentException {
        Client client = clientRepo.findById(addBusiness.clientId())
                .orElseThrow(() -> new IllegalArgumentException(
                "{message:"+ "\"No se encuentra una cuenta con el id= "+addBusiness.clientId()+"\"\n ,"+ "statusCode: Error }"));

        ListBusiness listBusiness = null;
        for (ListBusiness list : client.getListClient()) {
            if (list.getListName().equals(addBusiness.listId())) {
                listBusiness = list;
                break;
            }
        }

        if (listBusiness == null) throw new IllegalArgumentException(
                "{message:"+ "\"No se encuentra una lista con el id= "+addBusiness.listId()+"\"\n ,"+ "statusCode: Error }");

        listBusiness.getIdBusiness().add(addBusiness.businessId());
        clientRepo.save(client);

    }

    @Override
    public void deleteBusinessToList(BusinessToListDTO removeBusiness) throws IllegalArgumentException {
        Client client = clientRepo.findById(removeBusiness.clientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "{message:"+ "\"No se encuentra una cuenta con el id= "+removeBusiness.clientId()+"\"\n ,"+ "statusCode: Error }"));


        ListBusiness listBusiness = null;

        for (ListBusiness list : client.getListClient()) {
            if (list.getListName().equals(removeBusiness.listId())) {
                listBusiness = list;
                break;
            }
        }

        if (listBusiness == null) throw new IllegalArgumentException(
                "{message:"+ "\"No se encuentra una lista con el id= "+removeBusiness.listId()+"\"\n ,"+ "statusCode: Error }");

        listBusiness.getIdBusiness().remove(removeBusiness.businessId());
        clientRepo.save(client);
    }
}
