package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRep extends MongoRepository<Cliente, String> {


}
