package ttps.grupo2.appmascotas.dto;

import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.EstadoMascota;

import java.util.List;

public class MascotaResponseDTO {
    private Long id;
    private String nombre;
    private String color;
    private EstadoMascota estado;
    private List<String> fotos;
    private Coordenada coordenada;
    private String nombrePublicador;

    // Getter y Setter para id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Getter y Setter para estado
    public EstadoMascota getEstado() {
        return estado;
    }

    public void setEstado(EstadoMascota estado) {
        this.estado = estado;
    }

    // Getter y Setter para fotos
    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    // Getter y Setter para coordenada
    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    // Getter y Setter para nombrePublicador
    public String getNombrePublicador() {
        return nombrePublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        this.nombrePublicador = nombrePublicador;
    }
}
