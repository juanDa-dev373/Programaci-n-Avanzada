package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepo  extends MongoRepository<Business, String> {
    @Query("{ 'name': ?0, 'stateBusiness': 'ACTIVE', 'state': 'APPROVED' }")
    List<Business> findByName(String name);
    @Query("{ 'typeBusiness': ?0, 'stateBusiness': 'ACTIVE', 'state': 'APPROVED' }")
    List<Business> findByTypeBusiness(TypeBusiness type);
    @Query("{location : { $near : { $geometry : { type : 'Point', coordinates:[?0,?1] }, $maxDistance : ?2 }}, stateBusiness: 'ACTIVE', 'state': 'APPROVED'}")
    List<Business> findByLocationNear(double longitude, double latitude, double maxDistance);
    @Query("{'_id':?0 ,'stateBusiness':'ACTIVE', 'state': 'APPROVED' }")
    Optional<Business> findBusinessById(String id);
    @Query("{'_id':?0, 'state': ?1 }")
    Optional<Business> findBusinessByState(String id, StateBusiness stateBusiness);
    @Query("{'idClient': ?0, 'stateBusiness':'ACTIVE'}")
    List<Business> findBusinessByIdClient(String idClient);
    @Query("{ 'review.codeModerator': ?0}")
    List<Business> findBusinessByModerator(String id);
    @Query("{'stateBusiness': 'ACTIVE', 'state': 'APPROVED'}")
    List<Business> allBusiness();
}
