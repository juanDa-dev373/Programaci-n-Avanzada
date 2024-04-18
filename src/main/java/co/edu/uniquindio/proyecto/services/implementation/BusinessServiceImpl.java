package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepo businessRepo;
    private final ImageService imageService;

    @Override
    public void addBusiness(AddBusinessDTO addBusinessDto) throws Exception {
        if(existBusiness(addBusinessDto.id())) {
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
        if(updateBusinessDTO.idCliente().equals(business.getIdClient())) {
            business.setTypeBusiness(updateBusinessDTO.typeBusiness());
            business.setImages(updateBusinessDTO.images());
            business.setPhone(updateBusinessDTO.phone());
            business.setLocation(updateBusinessDTO.location());
            business.setName(updateBusinessDTO.name());
            business.setTimeSchedules(updateBusinessDTO.timeSchedules());
            business.setDescription(updateBusinessDTO.description());
            business.setReview(updateBusinessDTO.review());

            businessRepo.save(business);
        }else {
            throw new Exception("the client is not the owner");
        }
    }

    @Override
    public void deleteBusiness(DeleteBusinessDTO deleteBusinessDTO) throws Exception {
        Optional<Business> business = businessRepo.findBusinessById(deleteBusinessDTO.idBusiness());
        if(existBusiness(deleteBusinessDTO.idBusiness()) && business.get().getStateBusiness() == StateRecord.INACTIVE){
            throw new Exception("The Business don't exist");
        }
        Business business1 = business.get();
        if(business1.getIdClient().equals(deleteBusinessDTO.idClient())){
            business1.setStateBusiness(StateRecord.INACTIVE);
            businessRepo.save(business1);
        }else{
            throw new Exception("the client is not owner");
        }
    }

    @Override
    public List<Business> searchBusinessLocation(LocationDTO locationDTO) throws Exception {
        List<Business> businessList = businessRepo.findByLocationNear(locationDTO.location().getLongituded(), locationDTO.location().getLatitude(), locationDTO.maxDistance());
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
    public List<Business> listBusinessOwner(String idClient) throws Exception {
        List<Business> businessList = businessRepo.findBusinessByIdClient(idClient);
        if(businessList.isEmpty()){
            throw new Exception("No hay Negocios con ese dueño");
        }
        return businessList;
    }
    @Override
    public void registrerReview(RegistrerReviewDTO registrerReviewDTO) throws Exception {
        Optional<Business> businessOptional = businessRepo.findBusinessByState(registrerReviewDTO.id(), StateBusiness.PENDING);
        if(existBusiness(registrerReviewDTO.id()) && businessOptional.get().getState()== StateRecord.INACTIVE){
            throw new Exception("El negocio no existe");
        }
        Business business = businessOptional.get();
        if(registrerReviewDTO.review().getStateBusiness() == StateBusiness.APPROVED){
            business.setStateBusiness(StateBusiness.APPROVED);
        }
        if(registrerReviewDTO.review().getStateBusiness() == StateBusiness.REJECTED){
            business.setStateBusiness(StateBusiness.REJECTED);
        }
        business.setReview(registrerReviewDTO.review());
        businessRepo.save(business);
    }

    @Override
    public Business search(String id) throws Exception {
        Optional<Business> business = businessRepo.findBusinessById(id);
        if(business.isEmpty()){
            throw new Exception("El negocio no existe");
        }
        return business.get();
    }

    @Override
    public List<Business> listBusinessModerator(String idModerator) throws Exception {
        List<Business> businesses = businessRepo.findBusinessByModerator(idModerator);
        if(businesses.isEmpty()){
            throw new Exception("No hay negocios asosciados a el moderador");
        }
        return businesses;
    }

    @Override
    public List<Business> allBusiness() throws Exception {
        List<Business> busines = businessRepo.allBusiness();
        if(busines.isEmpty()){
            throw new Exception("No hay negocios");
        }
        return busines;
    }

    public boolean existBusiness(String id){
        return businessRepo.findBusinessById(id).isPresent();
    }

}
