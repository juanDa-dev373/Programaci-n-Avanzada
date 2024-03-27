package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.AccountDetailDTO;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Moderator;
import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.model.enums.StateRecord;
import co.edu.uniquindio.proyecto.repositories.BusinessRepo;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModeratorServiceImpl extends AccountServiceImpl implements ModeratorService {

    private final ModeratorRepo moderatorRepository;

    private final BusinessRepo businessRepository;

    private final ClientRepo clientRepository;

    public ModeratorServiceImpl(ModeratorRepo moderatorRepository, BusinessRepo businessRepository, ClientRepo clientRepository) {
        super(moderatorRepository);
        this.moderatorRepository = moderatorRepository;
        this.businessRepository = businessRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public AccountDetailDTO getModeratorById(String idModerator) throws Exception {
        //Buscamos el cliente que se quiere actualizar
        Optional<Moderator> optionalModerator = moderatorRepository.findById( idModerator );

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
    public String verifyAndApproveBusiness(String moderatorId, String placeId) {
        Business business=verifyModeratorBusiness(moderatorId,placeId);
        business.setState(StateBusiness.APPROVED);
        businessRepository.save(business);
        return business.getState().toString();
    }

    @Override
    public String rejectBusiness(String moderatorId, String placeId) {
        Business business= verifyModeratorBusiness(moderatorId,placeId);
        business.setState(StateBusiness.REJECTED);
        businessRepository.save(business);
        return business.getState().toString();
    }

    @Override
    public String deactivateUserAccount(String moderatorId, String userId) {
        Client client= verifyModeratorClient(moderatorId, userId);
        client.setState(StateRecord.INACTIVE);
        clientRepository.save(client);
        return client.getState().toString();
    }

    @Override
    public String activateUserAccount(String moderatorId, String userId) {
        Client client=verifyModeratorClient(moderatorId, userId);
        client.setState(StateRecord.ACTIVE);
        clientRepository.save(client);
        return client.getState().toString();
    }

    private Client verifyModeratorClient(String moderatorId, String userId) {
        Optional<Moderator> moderatorOptional = moderatorRepository.findById(moderatorId);
        if (moderatorOptional.isEmpty())
            throw new IllegalArgumentException(
                    "{message:"+ "\"No se encontró el cliente con el id= "+moderatorId+"\","+ "statusCode: Error }");

        Optional<Client> clientOptional = clientRepository.findById(userId);
        if (clientOptional.isEmpty())
                throw new IllegalArgumentException(
                        "{message:"+ "\"No se encontró el moderator con el id= "+userId+"\","+ "statusCode: Error }");

       return clientOptional.get();
    }

    private Business verifyModeratorBusiness(String moderatorId, String placeId) {
        Optional<Moderator> moderatorOptional = moderatorRepository.findById(moderatorId);
        if (moderatorOptional.isPresent())
            throw new IllegalArgumentException(
                    "{message:"+ "\"No se encontró el negocio con el id= "+placeId+"\","+ "statusCode: Error }");


        Optional<Business> place = businessRepository.findById(placeId);
        if (place.isEmpty())
            throw new IllegalArgumentException(
                    "{message:"+ "\"No se encontró el moderator con el id= "+moderatorId+"\","+ "statusCode: Error }");

        return place.get();
    }

    @Override
    public String markCommentAsInappropriate(String moderatorId, String commentId) throws Exception {
        return null;
    }
}
