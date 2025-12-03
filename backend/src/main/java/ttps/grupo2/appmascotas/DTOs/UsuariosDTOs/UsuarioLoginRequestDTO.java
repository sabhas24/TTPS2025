package ttps.grupo2.appmascotas.DTOs.UsuariosDTOs;

public class UsuarioLoginRequestDTO {
    private String email;
    private String contraseña;

    public UsuarioLoginRequestDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
