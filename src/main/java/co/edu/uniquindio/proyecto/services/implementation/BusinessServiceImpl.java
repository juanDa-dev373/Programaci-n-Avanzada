package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AddBusinessDTO;
import co.edu.uniquindio.proyecto.dto.ListBusinessOwnerDTO;
import co.edu.uniquindio.proyecto.dto.LocationDTO;
import co.edu.uniquindio.proyecto.dto.UpdateBusinessDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepo businessRepo;

    public BusinessServiceImpl(BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
    }

    @Override
    public void addBusiness(AddBusinessDTO addBusinessDto) throws Exception {
        if(!existBusiness(addBusinessDto.id())){
            new Exception("El Negocio ya existe");
        }
        Business business = new Business();
        business.setStateBusiness(StateRecord.ACTIVE);
        business.setState(StateBusiness.PENDING);
        business.setTypeBusiness(addBusinessDto.typeBusiness());
        business.setImages(addBusinessDto.images());
        business.setDescription(addBusinessDto.description());
        business.setName(addBusinessDto.name());
        business.setLocation(addBusinessDto.location());
        business.setIdClient(addBusinessDto.idClient());
        business.setReview(addBusinessDto.review());
        business.setTimeSchedules(addBusinessDto.timeSchedules());
        business.setPhone(addBusinessDto.phone());

        businessRepo.save(business);
    }

    @Override
    public void updateBusiness(UpdateBusinessDTO updateBusinessDTO) throws Exception {
        Optional<Business> bus= businessRepo.findBusinessById(updateBusinessDTO.id());
        if(bus.isPresent()){
            new Exception("the Business don't exist");
        }
        Business business = bus.get();
        business.setTypeBusiness(null);
    }

    @Override
    public void deleteBusiness(String id) throws Exception {
        Optional<Business> business = businessRepo.findBusinessById(id);
        if(existBusiness(id)){
            new Exception("The Business don't exist");
        }
        Business business1 = business.get();
        business1.setStateBusiness(StateRecord.INACTIVE);
        businessRepo.save(business1);
    }

    @Override
    public List<Business> searBusinessLocation(LocationDTO locationDTO) throws Exception {
        Point point = new Point(locationDTO.location().getLatitude(), locationDTO.location().getLongitude());

        List<Business> businessList = businessRepo.findByLocation(point, locationDTO.maxDistance());
        if(businessList.size()==0){
            new Exception("No se encontraron Negocios cerca");
        }
        return businessList;
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
    public boolean existBusiness(String id){
        return businessRepo.findBusinessById(id).isPresent();
    }
}
