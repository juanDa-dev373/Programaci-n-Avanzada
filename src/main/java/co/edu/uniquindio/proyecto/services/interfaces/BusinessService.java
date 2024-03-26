package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.AddBusinessDTO;
import co.edu.uniquindio.proyecto.dto.ListBusinessOwnerDTO;
import co.edu.uniquindio.proyecto.dto.LocationDTO;
import co.edu.uniquindio.proyecto.dto.UpdateBusinessDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.Location;

import java.util.List;

public interface BusinessService {
    void addBusiness(AddBusinessDTO addBusinessDto) throws Exception;
    void updateBusiness(UpdateBusinessDTO updateBusinessDTO) throws Exception;
    void deleteBusiness(String id) throws Exception;
    List<Business> searBusinessLocation(LocationDTO locationDTO) throws Exception;
    List<Business> searchBusiness(String type) throws Exception;
    void filterState() throws Exception;
    void listBusinessOwner(ListBusinessOwnerDTO listBusinessOwner) throws Exception;
    void changeState() throws Exception;
    void registrerReview() throws Exception;

}
