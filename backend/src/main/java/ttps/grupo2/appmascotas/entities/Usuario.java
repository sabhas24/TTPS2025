package ttps.grupo2.appmascotas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String contraseña;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 50)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String barrio;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(nullable = false)
    private boolean habilitado;

    @Column(nullable = false)
    private int puntos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String foto; // almacena Base64

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Usuario_Medalla", joinColumns = @JoinColumn(name = "Id_Usuario", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "Id_Medalla", referencedColumnName = "id"))
    private List<Medalla> medallas;

    @OneToMany(mappedBy = "publicador", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Mascota> mascotasPublicadas;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Avistamiento> avistamientosReportados;

    public Usuario() {
        this.puntos = 0;
        this.medallas = new ArrayList<>();
        this.mascotasPublicadas = new ArrayList<>();
        this.avistamientosReportados = new ArrayList<>();
        this.habilitado = true;
        this.tipo = TipoUsuario.USUARIO;
    }

    public Usuario(String nombre, String apellido, String email, String contraseña, String telefono, String barrio,
            String ciudad, String foto, TipoUsuario tipo) {
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
        this.tipo = tipo;
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
        if (this.medallas == null)
            this.medallas = new java.util.ArrayList<>();
        if (!this.medallas.contains(medalla)) {
            this.medallas.add(medalla);
        }
        if (medalla != null) {
            if (medalla.getUsuarios() == null)
                medalla.setUsuarios(new java.util.ArrayList<>());
            if (!medalla.getUsuarios().contains(this)) {
                medalla.getUsuarios().add(this);
            }
        }
    }

    public void agregarMascotaPublicada(Mascota mascota) {
        this.mascotasPublicadas.add(mascota);
    }

    public void agregarAvistamiento(Avistamiento avistamiento) {
        if (this.avistamientosReportados == null) {
            this.avistamientosReportados = new ArrayList<>();
        }
        this.avistamientosReportados.add(avistamiento);
        avistamiento.setUsuario(this); // 🔥 clave para mantener la relación sincronizada
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public Usuario editarPerfil(String nombre, String apellido, String telefono, String barrio, String ciudad,
            String foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.foto = foto;
        return this;
    }

    public void deshabilitarUsuario() {
        this.habilitado = false;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
