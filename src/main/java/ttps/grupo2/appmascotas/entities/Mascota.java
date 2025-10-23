package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double tamanio;
    private String color;
    private String descripcionExtra;

    @Enumerated(EnumType.STRING)
    private EstadoMascota estado;

    private LocalDate fechaPublicacion;

    @ElementCollection
    private List<String> fotos;

    private LocalDate fechaAdopcion;
    private boolean habilitado;

    @Embedded
    private Coordenada coordenada;

    @ManyToOne
    private Usuario publicador;

    // ✅ Nueva relación con Avistamiento
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avistamiento> avistamientos = new ArrayList<>();

    // Constructor vacío
    public Mascota() {
    }

    // Constructor completo
    public Mascota(String nombre, double tamanio, String color,
                   String descripcionExtra, EstadoMascota estado,
                   LocalDate fechaPublicacion, List<String> fotos,
                   Coordenada coordenada, Usuario publicador) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.color = color;
        this.descripcionExtra = descripcionExtra;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
        this.fotos = fotos;
        this.coordenada = coordenada;
        this.publicador = publicador;
        this.habilitado = true;
    }

    public void cambiarEstado(EstadoMascota nuevoEstado) {
        // Transición automática según el estado actual
        if ((this.estado == EstadoMascota.PERDIDO_PROPIO || this.estado == EstadoMascota.PERDIDO_AJENO)
                && nuevoEstado == EstadoMascota.RECUPERADO) {
            this.estado = EstadoMascota.RECUPERADO;
            return;
        }

        if (this.estado == EstadoMascota.RECUPERADO && nuevoEstado == EstadoMascota.EN_POSESION) {
            this.estado = EstadoMascota.EN_POSESION;
            return;
        }

        if (this.estado == EstadoMascota.EN_ADOPCION && nuevoEstado == EstadoMascota.ADOPTADO) {
            this.estado = EstadoMascota.ADOPTADO;
            this.fechaAdopcion = LocalDate.now();
            return;
        }

        // Si no aplica transición automática, se fuerza el cambio
        this.estado = nuevoEstado;

        // Registrar fecha de adopción si corresponde
        if (nuevoEstado == EstadoMascota.ADOPTADO) {
            this.fechaAdopcion = LocalDate.now();
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTamanio() {
        return tamanio;
    }

    public void setTamanio(double tamanio) {
        this.tamanio = tamanio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcionExtra() {
        return descripcionExtra;
    }

    public void setDescripcionExtra(String descripcionExtra) {
        this.descripcionExtra = descripcionExtra;
    }

    public EstadoMascota getEstado() {
        return estado;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public Usuario getPublicador() {
        return publicador;
    }

    public void setPublicador(Usuario publicador) {
        this.publicador = publicador;
    }

    public List<Avistamiento> getAvistamientos() {
        return avistamientos;
    }

    public void setAvistamientos(List<Avistamiento> avistamientos) {
        this.avistamientos = avistamientos;
    }

    // Otros métodos
    public void agregarFoto(String foto) {
        this.fotos.add(foto);
    }

    public void deshabilitarPublicacion() {
        this.habilitado = false;
    }

    public void agregarAvistamiento(Avistamiento avistamiento) {
        if (this.avistamientos == null) {
            this.avistamientos = new ArrayList<>();
        }
        this.avistamientos.add(avistamiento);
    }
}