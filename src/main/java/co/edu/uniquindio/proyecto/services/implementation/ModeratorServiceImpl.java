package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.dto.HistoryReviewDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.entity.HistoryReview;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ModeratorServiceImpl extends AccountServiceImpl implements ModeratorService {

    private final ModeratorRepo moderatorRepo;

    private final BusinessRepo businessRepo;

    private final ClientRepo clientRepo;

    public ModeratorServiceImpl(ModeratorRepo moderatorRepository, BusinessRepo businessRepository, ClientRepo clientRepository) {
        super(moderatorRepository);
        this.moderatorRepo = moderatorRepository;
        this.businessRepo = businessRepository;
        this.clientRepo = clientRepository;
    }

    @Override
    public AccountDetailDTO getModeratorById(String idModerator) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Moderator> optionalModerator = moderatorRepo.findById( idModerator );

        //Si no se encontró el cliente, lanzamos una excepción
        if(optionalModerator.isEmpty())
            throw new Exception(
                    "{message:"+ "\"No se encontró el moderator con el id= "+idModerator+"\","+ "statusCode: Error }");

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
    public String verifyAndApproveBusiness(HistoryReviewDTO reviewDTO) {

        Moderator moderator= existModerator(reviewDTO.idModerator());
        Business business= existBusiness(reviewDTO.idBusiness());
        business.setState(StateBusiness.APPROVED);
        HistoryReview history= new HistoryReview(
                    reviewDTO.description(),
                    StateBusiness.APPROVED,
                    LocalDateTime.now(),
                    reviewDTO.idModerator(),
                    reviewDTO.idBusiness()
        );
        business.setState(StateBusiness.APPROVED);
        moderator.getHistoryReview().add(history);
        businessRepo.save(business);
        moderatorRepo.save(moderator);
        return business.getState().toString();
    }

    @Override
    public String rejectBusiness(HistoryReviewDTO reviewDTO) {
        Moderator moderator= existModerator(reviewDTO.idModerator());
        Business business= existBusiness(reviewDTO.idBusiness());
        business.setState(StateBusiness.REJECTED);
        HistoryReview history= new HistoryReview(
                reviewDTO.description(),
                StateBusiness.REJECTED,
                LocalDateTime.now(),
                reviewDTO.idModerator(),
                reviewDTO.idBusiness()
        );
        business.setState(StateBusiness.REJECTED);
        moderator.getHistoryReview().add(history);
        businessRepo.save(business);
        moderatorRepo.save(moderator);
        return business.getState().toString();
    }

    @Override
    public String deactivateUserAccount(String moderatorId, String userId) {
        existModerator(moderatorId);
        Client client= existClient(userId);
        client.setState(StateRecord.INACTIVE);
        clientRepo.save(client);
        return client.getState().toString();
    }

    @Override
    public String activateUserAccount(String moderatorId, String userId) {
        Client client=existClient(userId);
        client.setState(StateRecord.ACTIVE);
        clientRepo.save(client);
        return client.getState().toString();
    }

    private Client existClient(String userId) {
        Optional<Client> clientOptional = clientRepo.findById(userId);
        if (clientOptional.isEmpty())
            throw new IllegalArgumentException(
                "{message:"+ "\"No se encontró el cliente con el id= "+userId+"\","+ "statusCode: Error }");
       return clientOptional.get();
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
                "{message:"+ "\"No se encontró el moderator con el id= "+moderatorId+"\","+ "statusCode: Error }");

        return moderatorOptional.get();
    }

    @Override
    public String markCommentAsInappropriate(String moderatorId, String commentId) throws Exception {
        return null;
    }
}
