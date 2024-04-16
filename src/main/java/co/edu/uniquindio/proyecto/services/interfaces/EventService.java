package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.DeleteEventDTO;
import co.edu.uniquindio.proyecto.dto.EventDTO;
import co.edu.uniquindio.proyecto.dto.GetEventDTO;
import co.edu.uniquindio.proyecto.dto.UpdateEventDTO;
import co.edu.uniquindio.proyecto.model.documents.Event;

import java.util.List;

public interface EventService {
    void createEvent(EventDTO event) throws Exception;
    void deleteEvent(DeleteEventDTO deleteEventDTO) throws Exception;
    List<Event> listEvent(String idBusiness) throws Exception;
    Event getEvent(GetEventDTO getEventDTO) throws Exception;
    void updateEvent(UpdateEventDTO updateEventDTO) throws Exception;
}
