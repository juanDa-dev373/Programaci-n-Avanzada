package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;

import java.util.List;

public interface ClientService extends AccountService {



    /**
     * Registra un nuevo usuario en la plataforma.
     *
     * @param signUpDTO Los datos de registro del usuario.
     * @return Un mensaje de confirmación del registro.
     * @throws Exception Sí ocurre un error durante el proceso de registro.
     */
    String signUpUser(SignUpDTO signUpDTO) throws Exception;

    /**
     * Obtener la ruta y dirección para llegar a un lugar deseado.
     * @param idClient El ID único del cliente.
     * @return Los detalles del cliente.
     */
    AccountDetailDTO getClientById(String idClient) throws Exception;

    /**
     * Obtener una lista de negocios.
     *
     * @return Una lista con los clientes.
     */
    ListBusinessDTO getListBusiness(String idClient, String nameList) throws Exception;

    /**
     * Obtener las listas de negocios del cliente.
     *
     * @return Las listas con los clientes.
     */
    List<ListBusiness> getListsBusinesses(String idClient) throws Exception;

    /**
     * Obtener la lista de clientes existente.
     *
     * @return Una lista con los clientes.
     */
    List<ItemClientDTO> listClient() ;
    /*
     * Obtener la ruta y dirección para llegar a un lugar deseado.
     *
     * @param userId El ID único del usuario que solicita la ruta.
     * @param placeId El ID único del lugar al que se desea llegar.
     * @return La ruta y dirección para llegar al lugar, incluyendo el tiempo aproximado y la distancia en kilómetros.
     */
    /*
    RouteDetailsDTO getRouteToPlace(String userId, String placeId) throws Exception;
    */
    /**
     * Crea una lista de negocios para el cliente.
     *
     * @param clientId El ID del cliente para el que se creará la lista.
     * @param listName El nombre de la lista de negocios.
     * @return La lista de negocios creada.
     */
    ListBusiness createBusinessList(String clientId, String listName) throws Exception;

    /**
     * Elimina una lista de negocios del cliente.
     *
     * @param clientId     El ID del cliente del que se eliminará la lista.
     * @param idList La lista de negocios a eliminar.
     */
    void deleteBusinessList(String clientId, String idList) throws Exception;

    /**
     * Añade un negocio a una lista de negocios del cliente.
     *
     * @param addBusiness Contiene El ID del cliente, lista y el negocio que se añadirá
     */
    void addBusinessToList(BusinessToListDTO addBusiness) throws Exception;

    /**
     * Eliminar un negocio a una lista de negocios del cliente.
     *
     * @param removeBusiness Contiene El ID del cliente, lista y el negocio que se eliminará
     */
    void deleteBusinessToList(BusinessToListDTO removeBusiness) throws Exception;
    boolean existEmail(String email) throws Exception;
    Client getClient(String mail) throws Exception;
}
