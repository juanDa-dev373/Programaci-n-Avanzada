package co.edu.uniquindio.proyecto.services.interfaces;

public interface ModeradorServicio extends CuentaServicio{
    void authorize() throws Exception;
    void listBusiness() throws Exception;
}
