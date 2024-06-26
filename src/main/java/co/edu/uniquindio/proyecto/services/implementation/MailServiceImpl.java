package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    @Override
    public void sendMail(EmailDTO emailDTO) throws Exception {

        new Thread(() -> {
            try {
                MimeMessage mensaje = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mensaje);
                helper.setSubject(emailDTO.getAsunto());
                helper.setText(emailDTO.getCuerpo(), true);
                helper.setTo(emailDTO.getDestinatario());
                helper.setFrom("no_reply@dominio.com");

                javaMailSender.send(mensaje);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ).start();

    }

}
