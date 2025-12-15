import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioRegisterResponseDTO {

    @Schema(description = "Datos del usuario registrado")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Token JWT para autenticación")
    private String token;

    public UsuarioRegisterResponseDTO(UsuarioResponseDTO usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }
}
