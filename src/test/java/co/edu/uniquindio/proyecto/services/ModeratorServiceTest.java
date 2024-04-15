package co.edu.uniquindio.proyecto.services;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.dto.HistoryReviewDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.ReviewDTO;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ModeratorServiceTest {


    @Autowired
    private ModeratorService moderatorService;
    @Test
    public void deleteAccount() throws Exception {
        String state= moderatorService.deactivateAccount("Moderator1");
        Assertions.assertEquals(StateRecord.INACTIVE.toString(),state);
    }
    @Test
    public void deactivateUserAccount() throws Exception {
        String state= moderatorService.deactivateUserAccount("Moderator1","Cliente1");
        Assertions.assertEquals(StateRecord.INACTIVE.toString(),state);
    }
    @Test
    public void activateUserAccount() throws Exception {
        String state= moderatorService.activateUserAccount("Moderator1","Cliente1");
        Assertions.assertEquals(StateRecord.ACTIVE.toString(),state);
    }

    @Test
    public void logOutTest() throws Exception {
        moderatorService.logOutUser("Moderator1");

        //Con el método getModeratorById se obtiene el moderator con el ID "Moderator1"
        AccountDetailDTO moderator = moderatorService.getModeratorById("Moderator1");

        //Se verifica que se cambió el estado login
        Assertions.assertEquals("INACTIVE",moderator.login().toString());
    }

    @Test
    public void testVerifyAndApproveBusiness_Success() throws Exception {
        HistoryReviewDTO reviewDTO= new HistoryReviewDTO(
                "El negocio cumple con los requerimientos",
                "Moderator1", "Negocio1"
        );
        String result = moderatorService.verifyAndApproveBusiness(reviewDTO);
        Assertions.assertEquals(StateBusiness.APPROVED.toString(), result);
    }

    @Test
    public void testRejectBusiness_Success() throws Exception {
        HistoryReviewDTO reviewDTO= new HistoryReviewDTO(
                "El negocio NO cumple con los requerimientos",
                "Moderator1", "Negocio1"
        );
        String result = moderatorService.rejectBusiness(reviewDTO);
        Assertions.assertEquals(StateBusiness.REJECTED.toString(), result);
    }

    @Test
    public void getListHistoryReviews_Success() throws Exception{
        List <ReviewDTO> list= moderatorService.getListHistoryReviews("Moderator1");
        Assertions.assertEquals(2,list.size());
    }
}