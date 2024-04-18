package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Event;
import co.edu.uniquindio.proyecto.repositories.EventRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import co.edu.uniquindio.proyecto.services.interfaces.EventService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final ClientService clientService;
    private final BusinessService businessService;
    private final MailService mailService;


    @Override
    public void createEvent(EventDTO eventDTO) throws Exception {
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(eventDTO.id(), eventDTO.business(), eventDTO.client());
        if(eventOptional.isPresent()){
            throw new Exception("El evento ya existe");
        }
        //_______________________________
        AccountDetailDTO client = clientService.getClientById(eventDTO.client());
        Business business = businessService.search(eventDTO.business());
        //_______________________________
        Event event = new Event();
        event.setId(eventDTO.id());
        event.setDate(eventDTO.date());
        event.setBusiness(eventDTO.business());
        event.setClient(eventDTO.client());
        event.setTitle(eventDTO.title());
        event.setDescription(eventDTO.description());
        eventRepo.save(event);
        mailService.sendMail(new EmailDTO(
                "Creacion de evento",
                "<h1>Creacion correcta del evento</h1>"+
                        "<p> se creo el evento exitosamente al negocio"+business.getName()+
                        "<p>que esta asosiado al cliente "+client.nickname()+"</p´>",
                client.email()
        ));
    }

    @Override
    public void deleteEvent(DeleteEventDTO deleteEventDTO) throws Exception {
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(deleteEventDTO.id(), deleteEventDTO.idBusiness(), deleteEventDTO.idClient());
        if(!eventOptional.isPresent()){
            throw new Exception("El evento no existe");
        }
        eventRepo.deleteEventByIdAndBusinessAndClient(deleteEventDTO.id(), deleteEventDTO.idBusiness(), deleteEventDTO.idClient());
    }
    @Override
    public Event getEvent(GetEventDTO getEventDTO) throws Exception{
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(getEventDTO.id(), getEventDTO.idBusiness(),getEventDTO.idClient());
        if(!eventOptional.isPresent()){
            throw new Exception("El evento no existe");
        }
        return eventOptional.get();
    }

    @Override
    public void updateEvent(UpdateEventDTO updateEventDTO) throws Exception {
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(updateEventDTO.id(), updateEventDTO.business(), updateEventDTO.client());
        if(!eventOptional.isPresent()){
            throw new Exception("el evento no existe");
        }
        Event event = eventOptional.get();
        event.setDescription(updateEventDTO.description());
        event.setDate(updateEventDTO.date());
        event.setTitle(updateEventDTO.title());
        eventRepo.save(event);
    }

    @Override
    public List<Event> listEvent(String idBusiness) throws Exception {
        List<Event> eventList = eventRepo.findEventByBusiness(idBusiness);
        if(eventList.isEmpty()){
            throw new Exception("La lista es vacia");
        }
        return eventList;
    }
}
