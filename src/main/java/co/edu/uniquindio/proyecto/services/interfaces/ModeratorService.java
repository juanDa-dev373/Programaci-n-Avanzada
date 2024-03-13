package co.edu.uniquindio.proyecto.services.interfaces;

public interface ModeratorService extends AccountService {
    void authorize() throws Exception;
    void listBusiness() throws Exception;
}
