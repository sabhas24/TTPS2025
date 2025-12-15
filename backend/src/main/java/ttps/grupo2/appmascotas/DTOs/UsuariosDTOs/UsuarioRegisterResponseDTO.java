package ttps.grupo2.appmascotas.DTOs.UsuariosDTOs;

import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioResponseDTO;

public class UsuarioRegisterResponseDTO {

    private UsuarioResponseDTO usuario;
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
