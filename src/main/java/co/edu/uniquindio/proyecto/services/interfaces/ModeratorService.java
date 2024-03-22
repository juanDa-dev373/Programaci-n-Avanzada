package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.model.enums.StateBusiness;

public interface ModeratorService extends AccountService {
    void authorize(StateBusiness state) throws Exception;
    void listBusiness() throws Exception;
}
