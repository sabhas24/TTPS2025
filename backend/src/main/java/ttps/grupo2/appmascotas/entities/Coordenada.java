package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.*;

@Embeddable
public class Coordenada {

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    @Column(nullable = true, length = 100)
    private String barrio;

    public Coordenada() {
    }

    public Coordenada(double latitud, double longitud, String barrio) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.barrio = barrio;
    }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }
}