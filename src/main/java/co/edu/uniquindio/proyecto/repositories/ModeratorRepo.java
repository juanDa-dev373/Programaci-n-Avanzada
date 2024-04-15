package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Moderator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ModeratorRepo extends MongoRepository<Moderator, String> {
    @Query("{ 'email': ?0, 'state': 'ACTIVE' }")
    Optional<Moderator> findByEmail(String email);

}
