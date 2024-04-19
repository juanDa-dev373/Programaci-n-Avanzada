package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends AccountServiceImpl implements ClientService {

    private final BusinessService businessService;

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
     cliente.setProfilePhoto( sing.photo() );

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
                    "No se encuentra una cuenta con el id= "+profileDTO.id()
            );

        Client client = optionalClient.get();
        client.setName(profileDTO.name());
        client.setCity(profileDTO.city());
        client.setProfilePhoto(profileDTO.profilePhoto());

        clientRepo.save(client);

        mailService.sendMail(new EmailDTO(
                        "Cuenta Actualizada Exitosamente",
                        "      <h1>Felicitaciones por Crear su Cuenta Exitosamente</h1>\n" +
                                "        <p>¡Gracias por unirse a nuestra plataforma!</p>\n" +
                                "        <p>Su cuenta ha sido creada exitosamente.</p>\n" ,
                        client.getEmail()
                )

        );
    }

    @Override
    public ListBusinessDTO getListBusiness(String token, String nameList) throws Exception {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        Optional<Client> optionalClient = clientRepo.findById((String)jws.getPayload().get("id"));

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
                            b.getTypeBusiness(),
                            b.isOpen()
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
    public List<ListBusiness> getListsBusinesses(String token) throws Exception {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        Optional<Client> optionalClient = clientRepo.findById((String)jws.getPayload().get("id"));

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalClient.isEmpty()){
            throw new Exception("No se encontró el cliente a actualizar");
        }

        Client client = optionalClient.get();


        return client.getListClient();
    }

    @Override
    public boolean existEmail(String email) {
     return clientRepo.findByEmail(email).isPresent();
 }

    @Override
    public Client getClient(String mail) throws Exception {
        return clientRepo.findByEmail(mail).get();
    }


    private boolean existNickname(String nickname) {
        return clientRepo.findByNickname(nickname).isPresent();
 }

    @Override
    public AccountDetailDTO getClientById(String token) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        Optional<Client> optionalClient = clientRepo.findById((String)jws.getPayload().get("id"));

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
    public ListBusiness createBusinessList(String token, String listName)throws Exception {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        String idCliente= (String)jws.getPayload().get("id");

        Client client =clientRepo.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encuentra una cuenta con el id= "+ idCliente));

        if(listBusinessExistByName(idCliente,listName))
            throw new Exception( "El cliente ya tiene una lista con este nombre = "+listName);

        ListBusiness listBusiness = new ListBusiness();
        listBusiness.setListName(listName);
        listBusiness.setIdBusiness(new ArrayList<>());
        listBusiness.setId(""+ generateIdListBusiness(client.getId(),client.getListClient().size()));

        client.getListClient().add(listBusiness);

        clientRepo.save(client);

        return listBusiness;
    }

    private int generateIdListBusiness(String id, int base) {
        if(listBusinessExistById(id,""+(base+1)))
            return generateIdListBusiness(id, base+1);
        return base;
    }

    /**
     * Obtener la ruta y dirección para llegar a un lugar deseado.
     * @param idClient Se obtiene el ID único del cliente que desea buscar del token.
     * @param listName El nombre único de la lista de negocios crear.
     * @return True si existe en el cliente.
     */
    private boolean listBusinessExistByName(String idClient,String listName) {

        Client client = clientRepo.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encuentra una cuenta con el id= "+idClient));

        for (ListBusiness listBusiness : client.getListClient()) {
            if (listBusiness.getListName().equals(listName)) {
                return true;
            }
        }
        return false;
    }
    private boolean listBusinessExistById(String idClient,String id) {

        Client client = clientRepo.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encuentra una cuenta con el id= "+idClient));

        for (ListBusiness listBusiness : client.getListClient()) {
            if (listBusiness.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteBusinessList(String token, String listName) throws IllegalArgumentException{
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        String idCliente=(String)jws.getPayload().get("id");
        Client client =clientRepo.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encuentra una cuenta con el id= "+ idCliente ));

        ListBusiness listToRemove = null;
        for (ListBusiness listBusiness : client.getListClient()) {
            if (listBusiness.getListName().equals(listName)) {
                listToRemove = listBusiness;
                break;
            }
        }

        if (listToRemove == null) throw new IllegalArgumentException(
                "No se encuentra una lista con el id = "+listName);

        client.getListClient().remove(listToRemove);
        clientRepo.save(client);

    }

    @Override
    public void addBusinessToList(BusinessToListDTO addBusiness ,String token) throws IllegalArgumentException {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        Client client =clientRepo.findById((String)jws.getPayload().get("id"))
                .orElseThrow(() -> new IllegalArgumentException(
                "No se encuentra una cuenta con el id= "+addBusiness.clientId()+"\"\n ,"+ "statusCode: Error }"));

        ListBusiness listBusiness = null;
        for (ListBusiness list : client.getListClient()) {
            if (list.getId().equals(addBusiness.listId())) {
                listBusiness = list;
                break;
            }
        }

        if (listBusiness == null) throw new IllegalArgumentException(
                "No se encuentra una lista con el id= "+addBusiness.listId());

        listBusiness.getIdBusiness().add(addBusiness.businessId());
        clientRepo.save(client);

    }

    @Override
    public void deleteBusinessToList(BusinessToListDTO removeBusiness, String token) throws IllegalArgumentException {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        String idClient = (String)jws.getPayload().get("id");
        Client client =clientRepo.findById(idClient)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encuentra una cuenta con el id= "+removeBusiness.clientId()));


        ListBusiness listBusiness = null;

        for (ListBusiness list : client.getListClient()) {
            if (list.getId().equals(removeBusiness.listId())) {
                listBusiness = list;
                break;
            }
        }

        if (listBusiness == null) throw new IllegalArgumentException(
                "No se encuentra una lista con el id= "+removeBusiness.listId());

        listBusiness.getIdBusiness().remove(removeBusiness.businessId());
        clientRepo.save(client);
    }
}
