package ttps.grupo2.appmascotas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends Usuario{
    // TIENE SENTIDO PERSISTIR ESTA RELACIÓN?
    @OneToMany
    private List<Usuario> usuarios;
    @OneToMany
    private List<Mascota> publicaciones;

    public Admin() {
        super();
        usuarios = new ArrayList<>();
        publicaciones = new ArrayList<>();
    }

    public Admin(String nombre, String apellido, String email, String contraseña, String telefono, String barrio, String ciudad, String foto) {

        super(nombre, apellido, email, contraseña, telefono, barrio, ciudad, foto);
        usuarios = new ArrayList<>();
        publicaciones = new ArrayList<>();
    }

    // Getters y Setters

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Mascota> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Mascota> publicaciones) {
        this.publicaciones = publicaciones;
    }

    // Otros métodos

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void agregarPublicacion(Mascota mascota) {
        this.publicaciones.add(mascota);
    }

    public void borrarUsuario(Long id){

    }

    public void borrarPublicacion(Long id){

    }

    public Usuario modificarUsuario(Usuario usuario, Long id){
        return usuario;
    }

    public Mascota modificarPublicacion(Mascota mascota, Long id){
        return mascota;
    }

}
