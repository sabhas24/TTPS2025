package ttps.grupo2.appmascotas.persistence.dao;

import ttps.grupo2.appmascotas.entities.Usuario;

import java.util.List;

public interface UsuarioDAO extends GenericDAO<Usuario> {
    public Usuario buscarPorEmailYContraseña(String email, String contraseña);
    public Usuario getByEmail(String email);
    // Este método esta bien acá? Habría que parametrizarlo? Debería traer todo el usuario?
    public List<Usuario> getTopNUsuarios(int N);

    // Metodo para traer perfil? Metodo para traer lista de publicaciones?
}
