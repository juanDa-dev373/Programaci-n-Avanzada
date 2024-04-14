package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface MailService {
    void sendMail (EmailDTO mail) throws Exception;
}
