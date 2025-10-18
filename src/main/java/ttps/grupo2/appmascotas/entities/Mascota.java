package ttps.grupo2.appmascotas.entities;

import java.time.LocalDate;
import java.util.List;

public class Mascota {
    private Long id;
    private String nombre;
    private double tamanio;
    private String color;
    private String descripcionExtra;
    private EstadoMascota estado;
    private LocalDate fechaPublicacion;
    private List<String> fotos;
    private LocalDate fechaAdopcion;
    private Coordenada coordenada;
    private Usuario publicador;

    // Constructor
    public Mascota() {
    }

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
    }


    public void cambiarEstado(EstadoMascota nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado == EstadoMascota.ADOPTADO) {
            this.fechaAdopcion = LocalDate.now();
        }
    }

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

    public EstadoMascota getEstado() {
        return estado;
    }

    // Otros métodos
    public void agregarFoto(String foto) {
        this.fotos.add(foto);
    }
}
