package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest
public class BusinessServiceTest {

    @Autowired
    private BusinessService businessService;
    @Test
    public void addText() throws Exception{
        AddBusinessDTO addBusinessDTO = new AddBusinessDTO( "helados"
                , "este heladeria sirve helados muy rico",
                "cliente2", new Location( 4.53389, -75.68111),
                new ArrayList<>(Arrays.asList()),
                TypeBusiness.HELADERIA,
                new ArrayList<>(Arrays.asList(new Schedule("2:59:00", "Martes", "7:30:59")))
                , new ArrayList<>(Arrays.asList("43123")),
                null);
        businessService.addBusiness(addBusinessDTO,"test");
        Assertions.assertEquals(addBusinessDTO.name(),"negocio8", "El Negocio se registro");
    }
    @Test
    public void deleteTest() throws Exception{
        businessService.deleteBusiness(new DeleteBusinessDTO(
                "negocio3",
                "cliente2"
        ),"test");
        Business business = businessService.search("negocio3");
        Assertions.assertEquals(StateRecord.INACTIVE, business.getStateBusiness());
    }
    @Test
    public void UpdateTest() throws Exception{
        Business business = businessService.search("negocio8");
        UpdateBusinessDTO updateBusinessDTO = new UpdateBusinessDTO(
                "negocio8",
                "cliente2",
                "helado pelambre",
                "esto es una heladeria muy sabrosa",
                business.getLocation(),
                new ArrayList<>(),
                TypeBusiness.HELADERIA,
                business.getTimeSchedules(),
                business.getPhone(),
                business.getReview()
        );
        Assertions.assertNotEquals(business.getName(),updateBusinessDTO.name(),"Se debe cambiar el nombre");
        businessService.updateBusiness(updateBusinessDTO,"test");
    }
    @Test
    public void listTypeTest() throws Exception{
        List<Business> businessList = businessService.searchBusiness(TypeBusiness.HELADERIA);
        Assertions.assertEquals(1,businessList.size(), "la lista no es nula");
    }
    @Test
    public void listNameTest() throws Exception{
        List<Business> businessList = businessService.searchName("helados pelambre");
        Assertions.assertEquals(1,businessList.size(), "la lista no es nula");
    }
    @Test
    public void RegistrerReview() throws Exception{
        Business business = businessService.search("negocio6");
        RegistrerReviewDTO registrerReviewDTO = new RegistrerReviewDTO(
                "negocio6",
                new HistoryReview(
                        "no se acepto por mk",
                        StateBusiness.REJECTED,
                        LocalDateTime.now(),
                        "moderador1",
                        "neegocio6"
                )
        );
        businessService.registrerReview(registrerReviewDTO);
        Assertions.assertEquals(StateBusiness.REJECTED, registrerReviewDTO.review().getStateBusiness());
    }
    @Test
    public void listOwnerBusiness() throws Exception{
        List<Business> businesses = businessService.listBusinessOwner("cliente2");
        Assertions.assertEquals(4,businesses.size());

    }
    @Test
    public void listBusinessModerator() throws Exception{
        List<Business> businesses = businessService.listBusinessModerator("moderador1");
        Assertions.assertEquals(3,businesses.size());
    }
    @Test
    public void listLocationTest() throws Exception{
        LocationDTO locationDTO = new LocationDTO(new Location( 4.53389, -75.68111), 1000);
        List<Business> businessList = businessService.searchBusinessLocation(locationDTO);
        Assertions.assertEquals(1,businessList.size(), "la lista no es nula");
    }
}
