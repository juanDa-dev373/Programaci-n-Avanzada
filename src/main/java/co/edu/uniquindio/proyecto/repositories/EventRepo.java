package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends MongoRepository<Event, String> {

    Optional<Event> findEventByIdAndBusinessAndClient(String id, String business, String client);
    void deleteEventByIdAndBusinessAndClient(String id, String idBusiness, String idClient);
    List<Event> findEventByBusiness(String idBusiness);

}
