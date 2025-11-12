package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.entities.TipoUsuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // ✅ Buscar por email
    Optional<Usuario> findByEmail(String email);

    // ✅ Buscar por ciudad
    List<Usuario> findByCiudad(String ciudad);

    // ✅ Buscar usuarios habilitados
    List<Usuario> findByHabilitadoTrue();

    // ✅ Buscar por tipo de usuario
    List<Usuario> findByTipo(TipoUsuario tipo);

    // ✅ Buscar por email y contraseña (login básico)
    Optional<Usuario> findByEmailAndContraseña(String email, String contraseña);

    boolean existsByEmail(String email);
}