package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.DeleteEventDTO;
import co.edu.uniquindio.proyecto.dto.EventDTO;
import co.edu.uniquindio.proyecto.dto.UpdateEventDTO;
import co.edu.uniquindio.proyecto.model.documents.Event;
import co.edu.uniquindio.proyecto.repositories.EventRepo;
import co.edu.uniquindio.proyecto.services.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public void createEvent(EventDTO eventDTO) throws Exception {
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(eventDTO.id(), eventDTO.business(), eventDTO.client());
        if(eventOptional.isPresent()){
            throw new Exception("El evento ya existe");
        }
        Event event = new Event();
        event.setDate(eventDTO.date());
        event.setBusiness(eventDTO.business());
        event.setClient(eventDTO.client());
        event.setTitle(eventDTO.title());
        event.setDescription(eventDTO.description());
        eventRepo.save(event);
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
    public Event getEvent(String id, String idBusiness, String idClient) throws Exception{
        Optional<Event> eventOptional = eventRepo.findEventByIdAndBusinessAndClient(id,idBusiness,idClient);
        if(eventOptional.isEmpty()){
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
