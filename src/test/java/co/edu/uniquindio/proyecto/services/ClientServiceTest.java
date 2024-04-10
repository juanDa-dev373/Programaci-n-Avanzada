package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @Test
    void SignUpTest() throws Exception {

        SignUpDTO sing= new SignUpDTO(
                "Juan",
                "juan2410",
                "juanda@email.com",
                "1234",
                "My photo",
                "Armenia"
        );

        String code = clientService.signUpUser(sing);

        Assertions.assertNotNull(code);

    }

    @Test
    public void logInTest() throws Exception {
        LoginDTO login= new LoginDTO("juan@email.com","password");
        clientService.logInUser(login);

        //Con el método obtenerCliente se obtiene el cliente con el ID "Cliente1"
        AccountDetailDTO client = clientService.getClientById("Cliente1");

        //Se verifica que se cambió el estado login
        Assertions.assertEquals("ACTIVE",client.login().toString());
    }

    @Test
    public void listClientsTest(){
//Se obtiene la lista de clientes
        List<ItemClientDTO> lista = clientService.listClient();

//Se verifica que la lista no sea nula y que tenga 4 elementos
        Assertions.assertEquals(4, lista.size());

    }

    @Test
    public void deleteAccountTest() throws Exception {
        String remove= clientService.deactivateAccount("Cliente1");
        Assertions.assertEquals(StateRecord.INACTIVE.toString(),remove);
    }

    @Test
    public void getListBusinessTest() throws Exception {
        List<ListBusiness> list= clientService.getListsBusinesses("Cliente1");
        Assertions.assertEquals(1,list.size());
    }


    @Test
    public void updateProfileTest() throws Exception {
        ProfileDTO update=new ProfileDTO(
                "Cliente1",
                "David",
                "My photo",
                "Armenia"
        );
        clientService.updateProfile(update);

        //Con el método obtenerCliente se obtiene el cliente con el ID "Cliente1"
        AccountDetailDTO client = clientService.getClientById("Cliente1");

        //Se verifica que la foto de perfil sea la misma que se actualizó
        Assertions.assertEquals(update.name(), client.name());
    }

    @Test
    public void createBusinessListTest()throws Exception {
        ListBusiness list=clientService.createBusinessList("Cliente1","Restaurantes");
        Assertions.assertNotNull(list);
    }

    @Test
    public void deleteBusinessListTest() throws Exception{
        clientService.deleteBusinessList("Cliente1","Restaurantes");
        ListBusiness list=clientService.getListBusiness("Cliente1","Restaurantes");
        Assertions.assertNull(list);
    }

    @Test
    public void addBusinessToListTest() throws Exception {
        BusinessToListDTO addBusiness= new BusinessToListDTO(
                "Cliente1",
                "Favorites",
                "Negocio1"
        );
        clientService.addBusinessToList(addBusiness);
        ListBusiness list=clientService.getListBusiness("Cliente1","Favorites");
        Assertions.assertEquals("Negocio1",list.getIdBusiness().get(0));
    }


    @Test
    public void deleteBusinessToListTest() throws Exception {
        BusinessToListDTO addBusiness= new BusinessToListDTO(
                "Cliente1",
                "Favorites",
                "Negocio1"
        );
        clientService.deleteBusinessToList(addBusiness);
        ListBusiness list=clientService.getListBusiness("Cliente1","Favorites");
        Assertions.assertEquals(0,list.getIdBusiness().size());
    }

}
