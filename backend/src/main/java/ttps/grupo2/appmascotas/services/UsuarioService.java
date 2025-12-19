package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioUpdateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioRegisterResponseDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro de usuario
    public UsuarioRegisterResponseDTO registrar(UsuarioCreateRequestDTO dto) {
        try {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
            }

            Usuario nuevo = new Usuario();
            nuevo.setNombre(dto.getNombre());
            nuevo.setApellido(dto.getApellido());
            nuevo.setEmail(dto.getEmail());
            nuevo.setContraseña(passwordEncoder.encode(dto.getContraseña()));
            nuevo.setTelefono(dto.getTelefono());
            nuevo.setBarrio(dto.getBarrio());
            nuevo.setCiudad(dto.getCiudad());
            if (dto.getFoto() != null && !dto.getFoto().isBlank()) {
                nuevo.setFoto(dto.getFoto());
            }
            nuevo.setHabilitado(true);

            Usuario guardado = usuarioRepository.save(nuevo);

            String token = jwtService.generateToken(guardado);

            return new UsuarioRegisterResponseDTO(
                    convertirAResponseDTO(guardado),
                    token);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar: " + e.getMessage());
        }
    }

    // Edición de perfil
    public UsuarioResponseDTO editarPerfil(@NonNull Long id, UsuarioUpdateRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.editarPerfil(
                dto.getNombre(),
                dto.getApellido(),
                dto.getTelefono(),
                dto.getBarrio(),
                dto.getCiudad(),
                dto.getFoto());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirAResponseDTO(actualizado);
    }

    // Deshabilitar usuario
    public void deshabilitar(@NonNull Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuario.deshabilitarUsuario();
        usuarioRepository.save(usuario);
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioResponseDTO buscarPorId(@NonNull Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return convertirAResponseDTO(usuario);
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

    public List<UsuarioResponseDTO> buscarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    public void forgotPassword(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return; // No revelar si el email existe
        }

        Usuario usuario = usuarioOpt.get();
        String token = java.util.UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setResetTokenExpiration(java.time.LocalDateTime.now().plusHours(1)); // Validez de 1 hora
        usuarioRepository.save(usuario);

        String resetLink = "http://localhost:4200/auth/reset-password?token=" + token;
        String cuerpo = "Hola " + usuario.getNombre() + ",\n\n" +
                "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace:\n" +
                resetLink + "\n\n" +
                "Si no solicitaste esto, ignora este mensaje.";

        emailService.enviarEmail(usuario.getEmail(), "Restablecer Contraseña - AppMascotas", cuerpo);
    }

    public boolean resetPassword(String token, String nuevaPassword) {
        // Buscar usuario por token de recuperación
        Optional<Usuario> usuarioOpt = usuarioRepository.findByResetToken(token);
        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        // Verifica que el token no esté expirado
        if (usuario.getResetTokenExpiration() != null &&
                usuario.getResetTokenExpiration().isBefore(java.time.LocalDateTime.now())) {
            return false;
        }

        // Actualiza la contraseña y elimina el token
        usuario.setContraseña(passwordEncoder.encode(nuevaPassword));
        usuario.setResetToken(null);
        usuario.setResetTokenExpiration(null);
        usuarioRepository.save(usuario);
        return true;
    }
}