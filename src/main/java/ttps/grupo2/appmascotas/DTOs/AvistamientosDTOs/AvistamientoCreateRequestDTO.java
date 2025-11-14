package ttps.grupo2.appmascotas.DTOs.AvistamientosDTOs;

import ttps.grupo2.appmascotas.entities.Coordenada;

import java.util.List;

public class AvistamientoCreateRequestDTO {

    private Long mascotaId;
    private Long usuarioId;
    private Coordenada coordenada;
    private String comentario;
    private List<String> fotos;
    private boolean enPosesion;

    // Constructores
    public AvistamientoCreateRequestDTO() {
    }

    // Getters y Setters
    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(boolean enPosesion) {
        this.enPosesion = enPosesion;
    }
}


