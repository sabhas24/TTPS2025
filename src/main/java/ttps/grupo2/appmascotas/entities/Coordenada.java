package ttps.grupo2.appmascotas.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// Clase Coordenada
public class Coordenada {
    private Long id;
    private double latitud;
    private double longitud;
    private String barrio;

    public Coordenada() {
    }

    public Coordenada(double latitud, double longitud, String barrio) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.barrio = barrio;
    }


    public Long getId() { return id; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }
}