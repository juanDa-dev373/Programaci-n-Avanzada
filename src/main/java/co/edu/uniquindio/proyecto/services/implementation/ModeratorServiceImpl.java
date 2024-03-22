package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;
import co.edu.uniquindio.proyecto.repositories.ClientRepo;
import co.edu.uniquindio.proyecto.repositories.ModeratorRepo;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;
import org.springframework.stereotype.Service;

@Service
public class ModeratorServiceImpl extends AccountServiceImpl implements ModeratorService {

    private final ModeratorRepo moderatorRepo;

    public ModeratorServiceImpl(ModeratorRepo moderatorRepo) {
        super(moderatorRepo);
        this.moderatorRepo= moderatorRepo;
    }

    @Override
    public void authorize(StateBusiness state) throws Exception {

    }

    @Override
    public void listBusiness() throws Exception {

    }
}
