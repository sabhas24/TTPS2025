package ttps.grupo2.appmascotas.DTOs.MascotasDTOs;

import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.EstadoMascota;

import java.util.List;

public class MascotaResponseDTO {

    private Long id;
    private String nombre;
    private Double tamanio;
    private String color;
    private String descripcionExtra;
    private EstadoMascota estado;
    private List<String> fotos;
    private Coordenada coordenada;
    private Long publicadorId;
    private String nombrePublicador;

    public MascotaResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getTamanio() {
        return tamanio;
    }

    public void setTamanio(Double tamanio) {
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

    public void setEstado(EstadoMascota estado) {
        this.estado = estado;
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

    public Long getPublicadorId() {
        return publicadorId;
    }

    public void setPublicadorId(Long publicadorId) {
        this.publicadorId = publicadorId;
    }

    public String getNombrePublicador() {
        return nombrePublicador;
    }

    public void setNombrePublicador(String nombrePublicador) {
        this.nombrePublicador = nombrePublicador;
    }
}