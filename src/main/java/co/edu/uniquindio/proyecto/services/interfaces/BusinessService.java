package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;

import java.util.List;

public interface BusinessService {
    void addBusiness(AddBusinessDTO addBusinessDto) throws Exception;
    void updateBusiness(UpdateBusinessDTO updateBusinessDTO) throws Exception;
    void deleteBusiness(String id) throws Exception;
    List<Business> searchBusinessLocation(LocationDTO locationDTO) throws Exception;
    List<Business> searchBusiness(TypeBusiness type) throws Exception;
    List<Business> searchName(String name) throws Exception;
    List<Business> listBusinessOwner(String idClient) throws Exception;
    void registrerReview(RegistrerReviewDTO registrerReviewDTO) throws Exception;
    Business search(String id);
    List<Business> listBusinessModerator(String idModerator) throws Exception;
}
