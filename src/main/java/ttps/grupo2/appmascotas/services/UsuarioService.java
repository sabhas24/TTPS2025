package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registro de usuario
    public Usuario registrar(Usuario nuevo) {
        if (usuarioRepository.existsByEmail(nuevo.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }
        return usuarioRepository.save(nuevo);
    }

    // Edición de perfil
    public Usuario editarPerfil(Long id, Usuario datos) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.editarPerfil(
                datos.getNombre(),
                datos.getApellido(),
                datos.getTelefono(),
                datos.getBarrio(),
                datos.getCiudad(),
                datos.getFoto()
        );
        return usuarioRepository.save(usuario);
    }

    // Deshabilitar usuario
    public void deshabilitar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.deshabilitarUsuario();
        usuarioRepository.save(usuario);
    }

    // Login básico (sin seguridad avanzada)
    public Usuario login(String email, String contraseña) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email no registrado"));

        if (!usuario.getContraseña().equals(contraseña)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        if (!usuario.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario deshabilitado");
        }

        return usuario;
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}