package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends MongoRepository<Client, String> {
    @Query("{ 'nickname': ?0, 'state': 'ACTIVE' }")
    Optional<Client> findByNickname(String nickname);
    @Query("{ 'email': ?0, 'state': 'ACTIVE' }")
    Optional<Client> findByEmail(String email);

}
