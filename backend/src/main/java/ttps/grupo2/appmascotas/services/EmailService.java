package ttps.grupo2.appmascotas.services;

public interface EmailService {
    void enviarEmail(String destinatario, String asunto, String cuerpo);
}
