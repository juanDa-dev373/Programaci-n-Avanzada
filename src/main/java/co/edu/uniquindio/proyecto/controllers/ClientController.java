package co.edu.uniquindio.proyecto.controllers;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Comment;
import co.edu.uniquindio.proyecto.model.documents.Event;
import co.edu.uniquindio.proyecto.model.entity.ListBusiness;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    private final ClientService clientService;
    private final BusinessService businessService;
    private final CommentService commentService;
    private final EventService eventService;

    @GetMapping("/")
    public ResponseEntity<MensajeDTO<AccountDetailDTO>> getClientById(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  clientService.getClientById(token.replace("Bearer ", ""))));
    }

    @GetMapping("/listBusiness")
    public ResponseEntity<MensajeDTO<ListBusinessDTO>> getListBusiness(@RequestHeader("Authorization") String token, @Valid @RequestParam("nameList") String nameList) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.getListBusiness(token.replace("Bearer ", ""), nameList)));
    }

    @GetMapping("/listsBusinesses")
    public ResponseEntity<MensajeDTO<List<ListBusiness>>> getListsBusinesses(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.getListsBusinesses(token.replace("Bearer ", ""))));
    }

    @GetMapping("/listClient")
    public ResponseEntity<MensajeDTO<List<ItemClientDTO>>> listClient() {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.listClient()));
    }

    @PostMapping("/createBusinessList")
    public ResponseEntity<MensajeDTO<ListBusiness>> createBusinessList(@RequestHeader("Authorization") String token, @RequestParam("listName") String listName) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, clientService.createBusinessList(token.replace("Bearer ", ""), listName)));
    }

    @DeleteMapping("/deleteBusinessList")
    public ResponseEntity<MensajeDTO<String>> deleteBusinessList(@RequestHeader("Authorization") String token, @RequestParam("listName") String listName) throws Exception {
        clientService.deleteBusinessList(token.replace("Bearer ", ""), listName);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Lista de negocios eliminada correctamente"));
    }

    @PostMapping("/addBusinessToList")
    public ResponseEntity<MensajeDTO<String>> addBusinessToList(@Valid @RequestBody BusinessToListDTO addBusiness, @RequestHeader("Authorization") String token) throws Exception {
        clientService.addBusinessToList(addBusiness,token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio añadido a la lista correctamente"));
    }

    @DeleteMapping("/deleteBusinessToList")
    public ResponseEntity<MensajeDTO<String>> deleteBusinessToList(@Valid @RequestBody BusinessToListDTO removeBusiness, @RequestHeader("Authorization") String token) throws Exception {
        clientService.deleteBusinessToList(removeBusiness,token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio eliminado de la lista correctamente"));
    }
    @PostMapping("/addBusinessClient")
    public ResponseEntity<MensajeDTO<String>> addBusiness(@Valid @RequestBody AddBusinessDTO addBusinessDTO , @RequestHeader("Authorization") String token) throws Exception {
        businessService.addBusiness(addBusinessDTO,token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio Creado Exitosamente"));
    }

    @DeleteMapping("/deleteBusinessClient")
    ResponseEntity<MensajeDTO<String>> deleteBusiness(@Valid @RequestBody DeleteBusinessDTO deleteBusinessDTO, @RequestHeader("Authorization") String token) throws Exception {
        businessService.deleteBusiness(deleteBusinessDTO,token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Negocio Eliminado Correctamente"));
    }
    @PostMapping("/updateBusiness")
    ResponseEntity<MensajeDTO<String>> updateBusiness(@Valid @RequestBody UpdateBusinessDTO updateBusinessDTO, @RequestHeader("Authorization") String token) throws Exception {
        businessService.updateBusiness(updateBusinessDTO, token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El Negocio Fue Actualizado"));
    }
    @GetMapping("/getAllBusiness")
    ResponseEntity<MensajeDTO<List<Business>>> getAllBusiness() throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.allBusiness()));
    }
    @GetMapping("/listBusinessLocation")
    ResponseEntity<MensajeDTO<List<Business>>> listBusinessLocation(@Valid @RequestBody LocationDTO locationDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.searchBusinessLocation(locationDTO)));
    }
    @GetMapping("/listBusinessName/{name}")
    ResponseEntity<MensajeDTO<List<Business>>> listBusinessName(@Valid @PathVariable String name) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.searchName(name)));
    }
    @GetMapping("/listBusinessType/{type}")
    ResponseEntity<MensajeDTO<List<Business>>> listBusinessType(@Valid @PathVariable TypeBusiness type) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.searchBusiness(type)));
    }
    @GetMapping("/listBusinessOwner")
    ResponseEntity<MensajeDTO<List<Business>>> listBusinessOwner(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.listBusinessOwner(token.replace("Bearer ", ""))));
    }
    @GetMapping("/getBusiness/{idBusiness}")
    ResponseEntity<MensajeDTO<Business>> getBusiness(@Valid @PathVariable String idBusiness) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, businessService.search(idBusiness)));
    }
    @PostMapping("/createComment")
    ResponseEntity<MensajeDTO<String>> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) throws Exception {
        commentService.createComentary(createCommentDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Comentario Registrado"));
    }
    @PostMapping("/responseComment")
    ResponseEntity<MensajeDTO<String>> responseComment(@Valid @RequestBody ResponseCommentDTO responseCommentDTO) throws Exception{
        commentService.ResponseComentary(responseCommentDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha registrado la respuesta"));
    }
    @GetMapping("/{idBusiness}/listComment")
    ResponseEntity<MensajeDTO<List<Comment>>> listComment(@Valid @PathVariable String idBusiness) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, commentService.listComentary(idBusiness)));
    }
    @PostMapping("/calification")
    ResponseEntity<MensajeDTO<String>> calification(@Valid @RequestBody CalificationDTO calificationDTO) throws Exception{
        commentService.calification(calificationDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "se registro la calificacion"));
    }
    @GetMapping("/getComment")
    ResponseEntity<MensajeDTO<Comment>> getComment(@Valid @RequestParam("idComment") String idComment, @Valid @RequestParam("idBusiness") String idBusiness) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, commentService.getComment(idComment, idBusiness)));
    }
    @DeleteMapping("/deleteComment")
    ResponseEntity<MensajeDTO<String>> deleteComment(@Valid @RequestBody DeleteCommentDTO deleteCommentDTO) throws Exception {
        commentService.deleteComment(deleteCommentDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Comentario eliminado correctamente"));
    }
    @PostMapping("/createEvent")
    ResponseEntity<MensajeDTO<String>> createEvent(@Valid @RequestBody EventDTO eventDTO) throws Exception {
        eventService.createEvent(eventDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se creo el evento correctamente"));
    }
    @GetMapping("{idBusiness}/listEventBusiness")
    ResponseEntity<MensajeDTO<List<Event>>> listEventBusiness(@Valid @PathVariable String idBusiness) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, eventService.listEvent(idBusiness)));
    }
    @PostMapping("/updateEvent")
    ResponseEntity<MensajeDTO<String>> updateEvent(@Valid @RequestBody UpdateEventDTO updateEventDTO) throws Exception {
        eventService.updateEvent(updateEventDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se actualizo el evento correctamente"));
    }
    @GetMapping("/getEvent")
    ResponseEntity<MensajeDTO<Event>> getEvent(@Valid @RequestBody GetEventDTO getEventDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, eventService.getEvent(getEventDTO.id(),getEventDTO.idBusiness(),getEventDTO.idClient())));
    }
    @DeleteMapping("/deleteEvent")
    ResponseEntity<MensajeDTO<String>> deleteEvent(@Valid @RequestBody DeleteEventDTO deleteEventDTO) throws Exception {
        eventService.deleteEvent(deleteEventDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "se elimino correctamente el evento"));
    }
    @PostMapping("/logOutUser")
    public ResponseEntity<MensajeDTO<String>> logOutUser( @RequestHeader("Authorization") String token) throws Exception {
        clientService.logOutUser(token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Cierre exitoso" ));
    }
}
