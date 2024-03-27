package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Moderator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ModeratorRepo extends MongoRepository<Moderator, String> {
    Optional<Moderator> findByEmail(String email);
}
