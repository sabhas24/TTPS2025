package ttps.grupo2.appmascotas.DTOs.Avistamientos;

import ttps.grupo2.appmascotas.entities.Coordenada;

import java.time.LocalDateTime;
import java.util.List;

import io.micrometer.common.lang.NonNull;

public class AvistamientoCreateRequestDTO {
    @NonNull
    private Long mascotaId;
    @NonNull
    private Long usuarioId;
    private Coordenada coordenada;
    private LocalDateTime fecha;
    private String comentario;
    private List<String> foto;
    private Boolean enPosesion;

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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public Boolean getEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(Boolean enPosesion) {
        this.enPosesion = enPosesion;
    }

}
