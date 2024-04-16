package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.DeleteEventDTO;
import co.edu.uniquindio.proyecto.dto.EventDTO;
import co.edu.uniquindio.proyecto.dto.GetEventDTO;
import co.edu.uniquindio.proyecto.dto.UpdateEventDTO;
import co.edu.uniquindio.proyecto.model.documents.Event;
import co.edu.uniquindio.proyecto.services.interfaces.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Test
    public void createEvent() throws Exception{
        EventDTO eventDTO = new EventDTO(
                "event6",
                "este evento se creo con el fin de poder celebrar " +
                        "el cumpleaños de nuestros clientes el mes actual",
                LocalDateTime.now(),
                "cumpleaños",
                "client3",
                "negocio4"
        );
        eventService.createEvent(eventDTO);
        Event event = eventService.getEvent(new GetEventDTO("event6","client3","negocio4"));
        Assertions.assertEquals(eventDTO.id(), event.getId());
    }
    @Test
    public void deleteEventTest() throws Exception{
        DeleteEventDTO deleteEventDTO = new DeleteEventDTO(
                "event1",
                "negocio3",
                "client2"
        );
        GetEventDTO getEventDTO = new GetEventDTO(
                "event1",
                "negocio3",
                "client2");
        eventService.deleteEvent(deleteEventDTO);
        Assertions.assertThrows(Exception.class,()->{
           Event event = eventService.getEvent(getEventDTO);
        });
    }
    @Test
    public void listEventTest() throws Exception{
        List<Event> eventList = eventService.listEvent("negocio4");
        Assertions.assertEquals(2, eventList.size());
    }
    @Test
    public void updateEventTest() throws Exception{

        Event event = eventService.getEvent(new GetEventDTO("event6","negocio4","client3"));
        UpdateEventDTO updateEventDTO = new UpdateEventDTO(
                "event6",
                "este evento se creo con el fin de poder celebrar " +
                        "el cumpleaños de nuestros clientes el mes actual",
                LocalDateTime.now(),
                "fiesta pagana",
                "client3",
                "negocio4"
        );
        Assertions.assertNotEquals(event.getTitle(), updateEventDTO.title(), "tienes que cambiar el titulo");
        eventService.updateEvent(updateEventDTO);
    }

}
