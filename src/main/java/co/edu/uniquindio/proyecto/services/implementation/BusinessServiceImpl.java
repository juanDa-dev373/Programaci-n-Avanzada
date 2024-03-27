package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AddBusinessDTO;
import co.edu.uniquindio.proyecto.dto.ListBusinessOwnerDTO;
import co.edu.uniquindio.proyecto.dto.LocationDTO;
import co.edu.uniquindio.proyecto.dto.UpdateBusinessDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
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
        if(existBusiness(addBusinessDto.id())){
            throw new Exception("El Negocio ya existe");
        }
        Business business = new Business();
        business.setId(addBusinessDto.id());
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
        if(bus.isPresent() && bus.get().getStateBusiness()==StateRecord.INACTIVE){
            throw new Exception("the Business don't exist");
        }
        Business business = bus.get();
        business.setTypeBusiness(updateBusinessDTO.typeBusiness());
        business.setImages(updateBusinessDTO.images());
        business.setPhone(updateBusinessDTO.phone());
        business.setLocation(updateBusinessDTO.location());
        business.setName(updateBusinessDTO.name());
        business.setTimeSchedules(updateBusinessDTO.timeSchedules());
        business.setDescription(updateBusinessDTO.description());
        business.setReview(updateBusinessDTO.review());

        businessRepo.save(business);
    }

    @Override
    public void deleteBusiness(String id) throws Exception {
        Optional<Business> business = businessRepo.findBusinessById(id);
        if(existBusiness(id) && business.get().getStateBusiness() == StateRecord.INACTIVE){
            throw new Exception("The Business don't exist");
        }
        Business business1 = business.get();
        business1.setStateBusiness(StateRecord.INACTIVE);
        businessRepo.save(business1);
    }

    @Override
    public List<Business> searchBusinessLocation(LocationDTO locationDTO) throws Exception {
        Point point = new Point(locationDTO.location().getLatitude(), locationDTO.location().getLongitude());

        List<Business> businessList = businessRepo.findByLocation(point, locationDTO.maxDistance());
        if(businessList.isEmpty()){
            throw new Exception("No se encontraron Negocios cerca");
        }
        return businessList;
    }

    @Override
    public List<Business> searchBusiness(TypeBusiness type) throws Exception {
        List<Business> list = businessRepo.findByTypeBusiness(type);
        if (list.isEmpty()){
            throw new Exception("no matches found");
        }
        return list;
    }

    @Override
    public List<Business> searchName(String name) throws Exception{

        List<Business> businessList = businessRepo.findByName(name);
        if(businessList.isEmpty()){
           throw new Exception("no matches found");
        }
        return businessList;
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

    @Override
    public Business search(String id) {
        Optional<Business> business = businessRepo.findBusinessById(id);
        if(business.isPresent()){
            return business.get();
        }
        return null;
    }

    public boolean existBusiness(String id){
        return businessRepo.findBusinessById(id).isPresent();
    }
}
