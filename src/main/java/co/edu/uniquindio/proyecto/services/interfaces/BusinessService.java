package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;

import java.util.List;

public interface BusinessService {
    void addBusiness(AddBusinessDTO addBusinessDto , String token) throws Exception;
    void updateBusiness(UpdateBusinessDTO updateBusinessDTO, String token) throws Exception;
    void deleteBusiness(DeleteBusinessDTO deleteBusinessDTO, String token) throws Exception;
    List<Business> searchBusinessLocation(LocationDTO locationDTO) throws Exception;
    List<Business> searchBusiness(TypeBusiness type) throws Exception;
    List<Business> searchName(String name) throws Exception;
    List<Business> listBusinessOwner(String token) throws Exception;
    void registrerReview(RegistrerReviewDTO registrerReviewDTO) throws Exception;
    Business search(String id) throws Exception;
    List<Business> listBusinessModerator(String token) throws Exception;
    List<Business> allBusiness() throws Exception;
}
