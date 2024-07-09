package it.epicode.valhallagaming.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendConfirmationEmail(String userEmail, String adminName, String adminEmail){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(userEmail);
            helper.setFrom("vladsophia@hotmail.it");
            helper.setSubject("Valhalla Gaming - Conferma Prenotazione");
            String text = "Gentile Utente,\n"
                    + "La sua prenotazione è stata confermata da " + adminName + ".\n"
                    + "Grazie per aver scelto Valhalla Gaming! \n"
                    + "Se ha bisogno di contattarci può scriverci a " + adminEmail;
            helper.setText(text);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDeleteEmail(String userEmail) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(userEmail);
            helper.setFrom("vladsophia@hotmail.it");
            helper.setSubject("Valhalla Gaming - Conferma Prenotazione");
            String text = "Gentile Utente,\n"
                    + "siamo spiacenti ma la sua prenotazione è stata annullata. \n"
                    + "Riprovi a prenotare un altra postazione o un'altra data";
            helper.setText(text);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
