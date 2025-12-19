package ttps.grupo2.appmascotas.DTOs.AuthDTOs;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordDTO {
    @NotBlank
    private String token;

    @NotBlank
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}