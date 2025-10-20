package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Avistamiento {
    @Id
    private Long id;
    private LocalDateTime fecha;
    private Coordenada coordenada;
    private String comentario;
    private List<String> fotos;
    private boolean enPosesion;
    private Mascota mascota;
    private Usuario usuario;

    // Constructor
    public Avistamiento() {
    }

    public Avistamiento(LocalDateTime fecha, Coordenada coordenada,
                        String comentario, List<String> foto, boolean enPosesion,
                        Mascota mascota, Usuario usuario) {
        this.fecha = fecha;
        this.coordenada = coordenada;
        this.comentario = comentario;
        this.fotos = fotos;
        this.enPosesion = enPosesion;
        this.mascota = mascota;
        this.usuario = usuario;
    }

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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

    public void setFoto(List<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(boolean enPosesion) {
        this.enPosesion = enPosesion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
