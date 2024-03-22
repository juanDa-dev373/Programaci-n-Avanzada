package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModeratorRepo extends MongoRepository<Moderator, String> {
}
