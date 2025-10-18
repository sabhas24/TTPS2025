package ttps.grupo2.appmascotas.entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends UsuarioBase{
    private String nombre;
    private String apellido;
    private String telefono;
    private String barrio;
    private String ciudad;
    private boolean habilitado;
    private int puntos;
    private List<Medalla> medallas;
    private List<Mascota> mascotasPublicadas;
    private List<Avistamiento> avistamientosReportados;
    private String foto; //¿Qué tipo de dato usamos para una foto?

    public Usuario(){
        super();
        this.puntos = 0;
        this.medallas = new ArrayList<>();
        this.mascotasPublicadas = new ArrayList<>();
        this.avistamientosReportados = new ArrayList<>();
        this.habilitado = true;
    }

    public Usuario(String nombre, String apellido, String email, String contraseña, String telefono, String barrio, String ciudad, String foto) {
        super(email, contraseña);

        puntos = 0;
        medallas = new ArrayList<>();
        mascotasPublicadas = new ArrayList<>();
        avistamientosReportados = new ArrayList<>();
        habilitado = true;

        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.foto = foto;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public List<Medalla> getMedallas() {
        return medallas;
    }

    public void setMedallas(List<Medalla> medallas) {
        this.medallas = medallas;
    }

    public List<Mascota> getMascotasPublicadas() {
        return mascotasPublicadas;
    }

    public void setMascotasPublicadas(List<Mascota> mascotasPublicadas) {
        this.mascotasPublicadas = mascotasPublicadas;
    }

    public List<Avistamiento> getAvistamientosReportados() {
        return avistamientosReportados;
    }

    public void setAvistamientosReportados(List<Avistamiento> avistamientosReportados) {
        this.avistamientosReportados = avistamientosReportados;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    // Métodos adicionales

    public void agregarMedalla(Medalla medalla) {
        this.medallas.add(medalla);
    }

    public void agregarMascotaPublicada(Mascota mascota) {
        this.mascotasPublicadas.add(mascota);
    }

    public void agregarAvistamiento(Avistamiento avistamiento) {
        this.avistamientosReportados.add(avistamiento);
    }
    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public Usuario editarPerfil(String nombre, String apellido, String telefono, String barrio, String ciudad, String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.foto = foto;
        return this;
    }

}
