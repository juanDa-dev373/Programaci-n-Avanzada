package co.edu.uniquindio.proyecto.controllers;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{idClient}")
    public ResponseEntity<MensajeDTO<AccountDetailDTO>> getClientById(@Valid @PathVariable String idClient) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  clientService.getClientById(idClient)));
    }

    @GetMapping("/{idClient}/listBusiness")
    public ResponseEntity<MensajeDTO<ListBusinessDTO>> getListBusiness(@Valid @PathVariable String idClient, @Valid @RequestParam("nameList") String nameList) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.getListBusiness(idClient, nameList)));
    }

    @GetMapping("/{idClient}/listsBusinesses")
    public ResponseEntity<MensajeDTO<List<ListBusiness>>> getListsBusinesses(@Valid @PathVariable String idClient) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.getListsBusinesses(idClient)));
    }

    @GetMapping("/listClient")
    public ResponseEntity<MensajeDTO<List<ItemClientDTO>>> listClient() {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.listClient()));
    }

    @PostMapping("/{clientId}/createBusinessList")
    public ResponseEntity<MensajeDTO<ListBusiness>> createBusinessList(@Valid @PathVariable String clientId, @RequestParam("listName") String listName) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.createBusinessList(clientId, listName)));
    }

    @DeleteMapping("/{clientId}/deleteBusinessList")
    public ResponseEntity<MensajeDTO<String>> deleteBusinessList(@Valid @PathVariable String clientId, @RequestParam("idList") String idList) throws Exception {
        clientService.deleteBusinessList(clientId, idList);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Lista de negocios eliminada correctamente"));
    }

    @PostMapping("/addBusinessToList")
    public ResponseEntity<MensajeDTO<String>> addBusinessToList(@Valid @RequestBody BusinessToListDTO addBusiness) throws Exception {
        clientService.addBusinessToList(addBusiness);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio añadido a la lista correctamente"));
    }

    @DeleteMapping("/deleteBusinessToList")
    public ResponseEntity<MensajeDTO<String>> deleteBusinessToList(@Valid @RequestBody BusinessToListDTO removeBusiness) throws Exception {
        clientService.deleteBusinessToList(removeBusiness);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio eliminado de la lista correctamente"));
    }
}
