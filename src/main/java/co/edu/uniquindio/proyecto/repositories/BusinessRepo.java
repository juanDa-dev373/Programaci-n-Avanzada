package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepo  extends MongoRepository<Business, String> {
    @Query("{ 'name': ?0, 'stateBusiness': 'ACTIVE' }")
    List<Business> findByName(String name);
    @Query("{ 'typeBusiness': ?0, 'stateBusiness': 'ACTIVE' }")
    List<Business> findByTypeBusiness(TypeBusiness type);
    @Query("{ 'Location' : { $near : { $geometry : { type : 'Point', coordinates: ?0 }, $maxDistance : ?1 } }, 'stateBusiness': 'ACTIVATE' }")
    List<Business> findByLocation(Point point, double maxDistance);
    Optional<Business> findBusinessById(String id);

}
