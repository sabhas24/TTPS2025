package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioUpdateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioLoginRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioResponseDTO;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registro de usuario
    public UsuarioResponseDTO registrar(UsuarioCreateRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellido(dto.getApellido());
        nuevo.setEmail(dto.getEmail());
        nuevo.setContraseña(dto.getContraseña());
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setBarrio(dto.getBarrio());
        nuevo.setCiudad(dto.getCiudad());
        nuevo.setFoto(dto.getFoto());
        nuevo.setHabilitado(true);

        Usuario guardado = usuarioRepository.save(nuevo);
        return convertirAResponseDTO(guardado);
    }

    // Edición de perfil
    public UsuarioResponseDTO editarPerfil(Long id, UsuarioUpdateRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.editarPerfil(
                dto.getNombre(),
                dto.getApellido(),
                dto.getTelefono(),
                dto.getBarrio(),
                dto.getCiudad(),
                dto.getFoto()
        );

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(actualizado);
    }

    // Deshabilitar usuario
    public void deshabilitar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.deshabilitarUsuario();
        usuarioRepository.save(usuario);
    }

    // Login básico (sin seguridad avanzada)
    public UsuarioResponseDTO login(UsuarioLoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email no registrado"));

        if (!usuario.getContraseña().equals(dto.getContraseña())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        if (!usuario.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario deshabilitado");
        }

        return convertirAResponseDTO(usuario);
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Conversión a DTO de respuesta
    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setBarrio(usuario.getBarrio());
        dto.setCiudad(usuario.getCiudad());
        dto.setFoto(usuario.getFoto());
        return dto;
    }
}