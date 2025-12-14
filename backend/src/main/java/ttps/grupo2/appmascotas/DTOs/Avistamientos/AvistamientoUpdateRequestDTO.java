package ttps.grupo2.appmascotas.DTOs.Avistamientos;

import ttps.grupo2.appmascotas.entities.Coordenada;
import java.util.List;

public class AvistamientoUpdateRequestDTO {
    private String comentario;
    private List<String> fotos;
    private Boolean enPosesion;
    private Coordenada coordenada;
    private Boolean habilitado;

    // Getters y setters
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

    public Boolean getEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(Boolean enPosesion) {
        this.enPosesion = enPosesion;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
}
