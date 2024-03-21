package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.dto.ItemClientDTO;
import co.edu.uniquindio.proyecto.dto.SingUpDTO;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clienteService;
    @Test
    void registrarTest() throws Exception {

        SingUpDTO sing= new SingUpDTO(
                "Juan",
                "juan24",
                "juancortes@email.com",
                "1234",
                "My photo",
                "Armenia"
        );

        String codigo = clienteService.singClient(sing);

        Assertions.assertNotNull(codigo);

    }

    @Test
    public void listarClientes(){
//Se obtiene la lista de clientes
        List<ItemClientDTO> lista = clienteService.listClient();

//Se verifica que la lista no sea nula y que tenga 3 elementos
        Assertions.assertEquals(4, lista.size());

    }

    @Test
    public void removeClient(){

    }
}
