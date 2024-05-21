package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @Test
    void SignUpTest() throws Exception {

        SignUpDTO sing= new SignUpDTO(
                "Juan David",
                "juan10F",
                "juand.riosf1@uqvirtual.edu.co",
                "1234",
                "foto.png",
                "Armenia"
        );

        String code = clientService.signUpUser(sing);

        Assertions.assertNotNull(code);

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
        String remove= clientService.deactivateAccount("","Cliente1");
        Assertions.assertEquals(StateRecord.INACTIVE.toString(),remove);
    }

    @Test
    public void getListsBusinessTest() throws Exception {
        List<ListBusiness> list= clientService.getListsBusinesses("Cliente1");
        Assertions.assertEquals(3,list.size());
    }

    @Test
    public void getListBusinessTest() throws Exception {
        ListBusinessDTO list= clientService.getListBusiness("Cliente1","Favorites");
        Assertions.assertEquals(1,list.businesses().size());
    }

    @Test
    public void updateProfileTest() throws Exception {
        ProfileDTO update=new ProfileDTO(
                "Cliente1",
                "David",
                "foto.png",
                "Armenia"
        );
        clientService.updateProfile(update,"");

        //Con el método obtenerCliente se obtiene el cliente con el ID "Cliente1"
        Client client = clientService.getClientById("Cliente1");

        //Se verifica que la foto de perfil sea la misma que se actualizó
        Assertions.assertEquals(update.name(), client.getName());
    }

    @Test
    public void createBusinessListTest()throws Exception {
        ListBusiness list=clientService.createBusinessList("Cliente1","Heladerias");
        Assertions.assertNotNull(list);
    }

    @Test
    public void deleteBusinessListTest() throws Exception{
        clientService.deleteBusinessList("Cliente1","Restaurantes");
        ListBusinessDTO list=clientService.getListBusiness("Cliente1","Restaurantes");
        Assertions.assertNull(list);
    }

    @Test
    public void addBusinessToListTest() throws Exception {
        BusinessToListDTO addBusiness= new BusinessToListDTO(
                "Cliente1",
                "Favorites",
                "Negocio1"
        );
        clientService.addBusinessToList(addBusiness,"test");
        ListBusinessDTO list=clientService.getListBusiness("Cliente1","Favorites");
        Assertions.assertEquals("Negocio1",list.businesses().get(0).name());
    }


    @Test
    public void deleteBusinessToListTest() throws Exception {
        BusinessToListDTO addBusiness= new BusinessToListDTO(
                "Cliente1",
                "Favorites",
                "Negocio1"
        );
        clientService.deleteBusinessToList(addBusiness,"test");
        ListBusinessDTO list=clientService.getListBusiness("Cliente1","Favorites");
        Assertions.assertEquals(0,list.businesses().size());
    }

}
