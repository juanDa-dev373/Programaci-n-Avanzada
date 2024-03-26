package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepo  extends MongoRepository<Business, String> {

    List<Business> findByName(String name);
    List<Business> fingByTypeBusiness(String type);
    @Query("{ 'Location' : { $near : { $geometry : { type : 'Point', coordinates: ?0 }, $maxDistance : ?1 } } }")
    List<Business> findByLocation(Point point, double maxDistancia);

    Optional<Business> findBusinessById(String id);

    @Override
    void deleteById(String id);
}
