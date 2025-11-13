package ttps.grupo2.appmascotas.dto;

import ttps.grupo2.appmascotas.entities.Coordenada;

import java.util.List;

public class MascotaRequestDTO {
    private String nombre;
    private double tamanio;
    private String color;
    private String descripcionExtra;
    private List<String> fotos;
    private Coordenada coordenada;
    private Long publicadorId;

    public String getNombre() {
        return nombre;
    }

    public double getTamanio() {
        return tamanio;
    }

    public String getColor() {
        return color;
    }

    public String getDescripcionExtra() {
        return descripcionExtra;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public Long getPublicadorId() {
        return publicadorId;
    }
}
