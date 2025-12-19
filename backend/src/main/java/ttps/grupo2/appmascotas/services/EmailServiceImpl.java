package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false) // Optional for now to avoid crashes if not configured
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@appmascotas.com}")
    private String remitente;

    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        if (mailSender == null) {
            System.out.println("⚠️ JavaMailSender no configurado. Logueando email:");
            System.out.println("To: " + destinatario);
            System.out.println("Subject: " + asunto);
            System.out.println("Body: " + cuerpo);
            return;
        }

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(remitente);
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            mailSender.send(mensaje);
        } catch (Exception e) {
            System.err.println("❌ Error al enviar email: " + e.getMessage());
            // Fallback logging
            System.out.println("To: " + destinatario);
            System.out.println("Subject: " + asunto);
            System.out.println("Body: " + cuerpo);
        }
    }
}
