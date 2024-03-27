package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.dto.AddBusinessDTO;
import co.edu.uniquindio.proyecto.dto.LocationDTO;
import co.edu.uniquindio.proyecto.dto.UpdateBusinessDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.entity.Location;
import co.edu.uniquindio.proyecto.model.entity.Schedule;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.model.enums.TypeBusiness;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.*;

@SpringBootTest
public class BusinessServiceTest {

    @Autowired
    private  BusinessService businessService;
    @Test
    public void addText() throws Exception{
        AddBusinessDTO addBusinessDTO = new AddBusinessDTO("negocio5", "helados"
                , "este heladeria sirve helados muy rico",
                "cliente2", new Location(75.12423, 0.2749138),
                new ArrayList<>(Arrays.asList("foto1","foto2")),
                TypeBusiness.HELADERIA,
                new ArrayList<>(Arrays.asList(new Schedule(LocalTime.of(2, 59, 00), "Martes", LocalTime.of(7, 30, 59))))
                , new ArrayList<>(Arrays.asList("43123")),
                null);
        businessService.addBusiness(addBusinessDTO);
        Business bus = businessService.search(addBusinessDTO.id());
        Assertions.assertEquals(bus.getId(),addBusinessDTO.id(), "El Negocio se registro");
    }
    @Test
    public void deleteTest() throws Exception{
        businessService.deleteBusiness("negocio3");
        Business business = businessService.search("negocio3");
        Assertions.assertEquals(StateRecord.INACTIVE, business.getStateBusiness());
    }
    @Test
    public void UpdateTest() throws Exception{
        Business business = businessService.search("negocio4");
        UpdateBusinessDTO updateBusinessDTO = new UpdateBusinessDTO(
                "negocio4",
                "helados pelambre",
                "esto es una heladeria muy sabrosa",
                business.getLocation(),
                business.getImages(),
                TypeBusiness.HELADERIA,
                business.getTimeSchedules(),
                business.getPhone(),
                business.getReview()
        );
        Assertions.assertNotEquals(business.getName(),updateBusinessDTO.name(),"Se debe cambiar el nombre");
        businessService.updateBusiness(updateBusinessDTO);
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
    public void listLocationTest() throws Exception{
        List<Business> businessList = businessService.searchBusinessLocation(null);
        Assertions.assertEquals(1,businessList.size(), "la lista no es nula");
    }

}
