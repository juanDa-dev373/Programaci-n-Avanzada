package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AddBusinessDTO;
import co.edu.uniquindio.proyecto.dto.ListBusinessOwnerDTO;
import co.edu.uniquindio.proyecto.dto.UpdateBusinessDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessServices {


    @Override
    public void addBusiness(AddBusinessDTO addBusinessDto) throws Exception {

    }

    @Override
    public void updateBusiness(UpdateBusinessDTO updateBusinessDTO) throws Exception {

    }

    @Override
    public void deleteBusiness(String id) throws Exception {

    }

    @Override
    public List<Business> searchBusiness(String type) throws Exception {
        return null;
    }

    @Override
    public void filterState() {

    }

    @Override
    public void listBusinessOwner(ListBusinessOwnerDTO listBusinessOwner) throws Exception {

    }
    @Override
    public void changeState() {

    }

    @Override
    public void registrerReview() throws Exception {

    }
}
