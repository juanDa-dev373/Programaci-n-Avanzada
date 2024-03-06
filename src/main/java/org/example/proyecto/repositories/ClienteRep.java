package org.example.proyecto.repositories;

import org.example.proyecto.model.documents.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRep extends MongoRepository<Cliente, String> {


}
