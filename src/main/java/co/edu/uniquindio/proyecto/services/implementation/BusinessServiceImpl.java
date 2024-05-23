package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepo businessRepo;
    private final JWTUtils jwtUtils;
    private final MailService mailService;
    @Override
    public void addBusiness(AddBusinessDTO addBusinessDto , String token) throws Exception {

        Business business = new Business();
        business.setState(StateRecord.ACTIVE);
        business.setStateBusiness(StateBusiness.PENDING);
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
    public void updateBusiness(UpdateBusinessDTO updateBusinessDTO,String token) throws Exception {
        Optional<Business> bus= businessRepo.findBusiness(updateBusinessDTO.id());
        if(bus.isPresent() && bus.get().getState()==StateRecord.INACTIVE){
            throw new Exception("the Business don't exist");
        }
        Business business = bus.get();
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        if((jws.getPayload().get("id")).equals(business.getIdClient())) {
            business.setTypeBusiness(updateBusinessDTO.typeBusiness());
            business.setImages(updateBusinessDTO.images());
            business.setPhone(updateBusinessDTO.phone());
            business.setLocation(updateBusinessDTO.location());
            business.setName(updateBusinessDTO.name());
            business.setTimeSchedules(updateBusinessDTO.timeSchedules());
            business.setDescription(updateBusinessDTO.description());
            business.setReview(updateBusinessDTO.review());

            businessRepo.save(business);
            mailService.sendMail(new EmailDTO(
                    "Negocio actualizado exitosamente",
                    "      <h1>Hola "+jws.getPayload().get("name")+", Usted actualizo su negocio identificado con el id:"+business.getId()+" </h1>\n" +
                            "        <p>¡Gracias por unirse a nuestra plataforma!</p>\n" +
                            "        <p>Su cuenta ha sido creada exitosamente.</p>\n" ,
                    jws.getPayload().getSubject()
            ));
        }else {
            throw new Exception("the client is not the owner");
        }
    }

    @Override
    public void deleteBusiness(DeleteBusinessDTO deleteBusinessDTO, String token) throws Exception {
        Optional<Business> business = businessRepo.findBusiness(deleteBusinessDTO.idBusiness());
        if(existBusiness(deleteBusinessDTO.idBusiness()) && business.get().getState() == StateRecord.INACTIVE){
            throw new Exception("The Business don't exist");
        }
        Business business1 = business.get();
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        if((jws.getPayload().get("id")).equals(business1.getIdClient())){
            business1.setState(StateRecord.INACTIVE);
            businessRepo.save(business1);
            mailService.sendMail(new EmailDTO(
                    "Negocio eliminado exitosamente",
                    "      <h1>Hola "+jws.getPayload().get("name")+", Usted elimino el negocio identificado con el id:"+business1.getId()+" </h1>\n" +
                            "        <p>¡Gracias por unirse a nuestra plataforma!</p>\n" +
                            "        <p>Su cuenta ha sido creada exitosamente.</p>\n" ,
                    jws.getPayload().getSubject()
            ));
        }else{
            throw new Exception("the client is not owner");
        }
    }

    @Override
    public List<Business> searchBusinessLocation(LocationDTO locationDTO) throws Exception {
        List<Business> businessList = businessRepo.findByLocationNear(locationDTO.location().getLongitude(), locationDTO.location().getLatitude(), locationDTO.maxDistance(),locationDTO.search());
        if(businessList.isEmpty()){
            throw new Exception("No se encontraron Negocios cerca");
        }
        return businessList;
    }

    @Override
    public List<Business> searchBusiness(String type) throws Exception {
        List<Business> list = businessRepo.findByTypeBusiness(type.toUpperCase());
        if (list.isEmpty()){
            throw new Exception("No se encontró negocio de tipo: \""+type+"\"");
        }
        return list;
    }

    @Override
    public List<Business> searchName(String name) throws Exception{

        List<Business> businessList = businessRepo.findByName(name);
        if(businessList.isEmpty()){
           throw new Exception("No se encontró negocio con el nombre: \""+name+"\"");
        }
        return businessList;
    }

    @Override
    public List<Business> listBusinessOwner(String token) throws Exception {
        Jws<Claims> jws = jwtUtils.parseJwt(token);
        String idClient= (String)jws.getPayload().get("id");
        List<Business> businessList = businessRepo.findBusinessByIdClient(idClient);
        if(businessList.isEmpty()){
            throw new Exception("No hay Negocios con ese dueño");
        }
        for(Business business: businessList){
            if(business.getStateBusiness().equals(StateBusiness.REJECTED)) {
                LocalDateTime reviewDate = business.getReview().getDate();
                LocalDateTime fiveDaysLater = reviewDate.plusDays(5);
                if (LocalDateTime.now().isAfter(fiveDaysLater)) {
                    // Ya pasaron 5 días después de la fecha de revisión
                    business.setState(StateRecord.INACTIVE);
                }
            }
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
        Optional<Business> optionalBusiness = businessRepo.findBusiness(id);
        if(optionalBusiness.isEmpty()){
            throw new Exception("El negocio no existe");
        }
        Business business=optionalBusiness.get();
        business.setOpen(isBusinessOpen(business.getTimeSchedules()));
        return business;
    }

    public boolean isBusinessOpen(List<Schedule> timeSchedules){
        String dayNow = LocalDateTime.now().getDayOfWeek().name();

        for (Schedule s: timeSchedules){
            if(dayNow.equalsIgnoreCase(s.getDay())) {
                CharSequence start = s.getStart();
                LocalTime timeStart = LocalTime.parse(start);
                CharSequence end = s.getEnd();
                LocalTime timeEnd = LocalTime.parse(end);
                if(LocalTime.now().isAfter(timeStart)&&LocalTime.now().isBefore(timeEnd)) return true;

            }
        }

        return false;
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
        List<Business> business = businessRepo.allBusiness();
        if(business.isEmpty()){
            throw new Exception("No hay negocios");
        }
        return business;
    }

    public boolean existBusiness(String id){
        return businessRepo.findBusiness(id).isPresent();
    }

}
