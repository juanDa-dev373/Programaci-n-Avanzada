package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.HistoryReviewDTO;
import co.edu.uniquindio.proyecto.dto.ReviewDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;
import co.edu.uniquindio.proyecto.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ModeratorServiceImpl extends AccountServiceImpl implements ModeratorService {

    private final BusinessRepo businessRepo;
    private final ClientService clientService;

    @Override
    public AccountDetailDTO getModeratorById(String idModerator) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Moderator> optionalModerator = moderatorRepo.findById( idModerator );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalModerator.isEmpty())
            throw new Exception(
                    "No se encontró el moderator con el id= "+idModerator);

        Moderator moderator = optionalModerator.get();
        //Obtenemos el cliente que se quiere actualizar y le asignamos los
        // nuevos valores (el nickname no se puede cambiar)
        return new AccountDetailDTO(
                moderator.getId(),
                moderator.getName(),
                moderator.getNickname(),
                moderator.getEmail(),
                moderator.getLogin()
        );
    }
    @Override
    public String verifyAndApproveBusiness(HistoryReviewDTO reviewDTO) throws Exception {

        Moderator moderator= existModerator(reviewDTO.idModerator());
        Business business= existBusiness(reviewDTO.idBusiness());
        business.setStateBusiness(StateBusiness.APPROVED);
        HistoryReview history= new HistoryReview(
                    reviewDTO.description(),
                    StateBusiness.APPROVED,
                    LocalDateTime.now(),
                    reviewDTO.idModerator(),
                    reviewDTO.idBusiness()
        );
        business.setStateBusiness(StateBusiness.APPROVED);
        moderator.getHistoryReview().add(history);
        businessRepo.save(business);
        moderatorRepo.save(moderator);
        mailService.sendMail(new EmailDTO(
                        "Negocio Aceptado Exitosamente",
                        "      <h1>Se negocio con id: "+business.getId()+" fue aceptado.\n observaciones:"+reviewDTO.description()
                                +"</h1>\n",
                        clientService.getClientId(business.getIdClient()).getEmail()
                )
        );
        return business.getStateBusiness().toString();
    }

    @Override
    public String rejectBusiness(HistoryReviewDTO reviewDTO) throws Exception {
        Moderator moderator= existModerator(reviewDTO.idModerator());
        Business business= existBusiness(reviewDTO.idBusiness());
        business.setStateBusiness(StateBusiness.REJECTED);
        HistoryReview history= new HistoryReview(
                reviewDTO.description(),
                StateBusiness.REJECTED,
                LocalDateTime.now(),
                reviewDTO.idModerator(),
                reviewDTO.idBusiness()
        );
        business.setStateBusiness(StateBusiness.REJECTED);
        moderator.getHistoryReview().add(history);
        businessRepo.save(business);
        moderatorRepo.save(moderator);
        mailService.sendMail(new EmailDTO(
                        "Negocio rechazado",
                        "      <h1>Se negocio con id: "+business.getId()+" fue rechazado tiene 5 dias hábiles para mejorarlo o será eliminado observaciones:"+reviewDTO.description()
                                +"</h1>\n",
                    clientService.getClientId(business.getIdClient()).getEmail()
                )
        );
        return business.getStateBusiness().toString();
    }

    @Override
    public String deactivateUserAccount(String token,String moderatorId, String userId) throws  Exception {

        return clientService.deactivateAccount(token,userId);

    }

    private Business existBusiness(String placeId) {
        Optional<Business> place = businessRepo.findById(placeId);
        if (place.isEmpty())
            throw new IllegalArgumentException(
                    "{message:"+ "\"No se encontró el negocio con el id= "+placeId+"\","+ "statusCode: Error }");

        return place.get();
    }

    private Moderator existModerator(String moderatorId) {
        Optional<Moderator> moderatorOptional = moderatorRepo.findById(moderatorId);
        if (moderatorOptional.isEmpty())
            throw new IllegalArgumentException(
                "No se encontró el moderator con el id= "+moderatorId);

        return moderatorOptional.get();
    }

    @Override
    public List<ReviewDTO> getListHistoryReviews(String moderatorId) throws Exception{
        Moderator moderator= existModerator(moderatorId);
        List<ReviewDTO> list=new ArrayList<>();
        for(HistoryReview review: moderator.getHistoryReview()){
            ReviewDTO history= new ReviewDTO(review.getStateBusiness(),moderatorId,review.getIdBusiness());
            list.add(history);
        }
        return list;
    }

    @Override
    public List<Business> allBusinessPending() throws Exception {
        List<Business> business = businessRepo.allBusinessPending();
        if(business.isEmpty()){
            throw new Exception("No hay negocios");
        }
        return business;
    }

    @Override
    public String markCommentAsInappropriate(String moderatorId, String commentId) throws Exception {
        return null;
    }


}
