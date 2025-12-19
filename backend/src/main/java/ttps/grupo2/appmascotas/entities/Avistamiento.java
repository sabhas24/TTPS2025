package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Avistamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Embedded
    private Coordenada coordenada;

    private String comentario;

    private boolean habilitado;

    @ElementCollection
    @CollectionTable(name = "avistamiento_fotos", joinColumns = @JoinColumn(name = "avistamiento_id"))
    @Column(name = "fotos", columnDefinition = "LONGTEXT")
    private List<String> fotos;

    private boolean enPosesion;

    @ManyToOne
    private Mascota mascota;

    @ManyToOne
    private Usuario usuario;

    // Constructor vacío
    public Avistamiento() {
    }

    // Constructor completo
    public Avistamiento(LocalDateTime fecha, Coordenada coordenada,
                        String comentario, List<String> fotos, boolean enPosesion,
                        Mascota mascota, Usuario usuario) {
        this.fecha = fecha;
        this.coordenada = coordenada;
        this.comentario = comentario;
        this.fotos = fotos;
        this.enPosesion = enPosesion;
        this.mascota = mascota;
        this.usuario = usuario;
        this.habilitado = true;
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

    public void setFotos(List<String> fotos) {
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

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}