package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String telefono;
    private String barrio;
    private String ciudad;
    private boolean habilitado;
    private int puntos;
    private String foto; //¿Qué tipo de dato usamos para una foto?

    @ManyToMany
    @JoinTable(name="Usuario_Medalla",
            joinColumns=@JoinColumn(name="Id_Usuario",
                    referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="Id_Medalla",
                    referencedColumnName="id"))
    private List<Medalla> medallas;

    @OneToMany(mappedBy = "publicador")
    private List<Mascota> mascotasPublicadas;

    @OneToMany(mappedBy = "usuario")
    private List<Avistamiento> avistamientosReportados;

    public Usuario(){
        this.puntos = 0;
        this.medallas = new ArrayList<>();
        this.mascotasPublicadas = new ArrayList<>();
        this.avistamientosReportados = new ArrayList<>();
        this.habilitado = true;
    }

    public Usuario(String nombre, String apellido, String email, String contraseña, String telefono, String barrio, String ciudad, String foto) {
        puntos = 0;
        medallas = new ArrayList<>();
        mascotasPublicadas = new ArrayList<>();
        avistamientosReportados = new ArrayList<>();
        habilitado = true;

        this.email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.foto = foto;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

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

    public void deshabilitarUsuario(){
        this.habilitado = false;
    }

}
