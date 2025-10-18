package ttps.grupo2.appmascotas.entities;

public abstract class UsuarioBase {
    private Long id;
    private String email;
    private String contraseña;

    public UsuarioBase(){
    }

    public UsuarioBase(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

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
}
