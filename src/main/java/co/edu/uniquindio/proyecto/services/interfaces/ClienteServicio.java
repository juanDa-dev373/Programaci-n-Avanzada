package co.edu.uniquindio.proyecto.services.interfaces;

public interface ClienteServicio extends CuentaServicio{

    void singupCostumer();

    void editProfile();

    void getCostumer();

    void removeCostumer();

    void deleteAccount();

    void login();

    void passwordRecovery();

}
