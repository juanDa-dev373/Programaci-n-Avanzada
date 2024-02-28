package repositorios;

import org.example.proyecto.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRep extends MongoRepository<Cliente, String> {


}
