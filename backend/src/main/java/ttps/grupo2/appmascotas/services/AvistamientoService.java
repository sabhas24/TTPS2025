package ttps.grupo2.appmascotas.services;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ttps.grupo2.appmascotas.DTOs.AvistamientosDTOs.AvistamientoCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.AvistamientosDTOs.AvistamientoResponseDTO;
import ttps.grupo2.appmascotas.entities.Avistamiento;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.AvistamientoRepository;
import ttps.grupo2.appmascotas.repositories.MascotaRepository;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvistamientoService {

    private final AvistamientoRepository avistamientoRepository;
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    // Constructor injection
    public AvistamientoService(AvistamientoRepository avistamientoRepository,
                               MascotaRepository mascotaRepository,
                               UsuarioRepository usuarioRepository) {
        this.avistamientoRepository = avistamientoRepository;
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Crear avistamiento
    public AvistamientoResponseDTO crearAvistamiento(AvistamientoCreateRequestDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!mascota.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede crear avistamiento de mascota deshabilitada");
        }

        Avistamiento avistamiento = new Avistamiento();
        avistamiento.setFecha(LocalDateTime.now());
        avistamiento.setCoordenada(dto.getCoordenada());
        avistamiento.setComentario(dto.getComentario());
        avistamiento.setFotos(dto.getFotos());
        avistamiento.setEnPosesion(dto.isEnPosesion());
        avistamiento.setMascota(mascota);
        avistamiento.setUsuario(usuario);
        avistamiento.setHabilitado(true);

        Avistamiento guardado = avistamientoRepository.save(avistamiento);
        return convertirAResponseDTO(guardado);
    }

    // Listar todos los avistamientos
    public List<AvistamientoResponseDTO> listarTodos() {
        return avistamientoRepository.findByHabilitadoTrue()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Listar avistamientos por mascota
    public List<AvistamientoResponseDTO> listarPorMascota(Long mascotaId) {
        return avistamientoRepository.findByMascotaIdAndHabilitadoTrue(mascotaId)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Listar avistamientos por usuario
    public List<AvistamientoResponseDTO> listarPorUsuario(Long usuarioId) {
        return avistamientoRepository.findByUsuarioIdAndHabilitadoTrue(usuarioId)
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Listar avistamientos en posesión
    public List<AvistamientoResponseDTO> listarEnPosesion() {
        return avistamientoRepository.findByEnPosesionTrueAndHabilitadoTrue()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Deshabilitar avistamiento
    public void deshabilitar(Long id) {
        Avistamiento avistamiento = avistamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avistamiento no encontrado"));

        avistamiento.setHabilitado(false);
        avistamientoRepository.save(avistamiento);
    }

    // Conversión a DTO de respuesta
    private AvistamientoResponseDTO convertirAResponseDTO(Avistamiento avistamiento) {
        AvistamientoResponseDTO dto = new AvistamientoResponseDTO();
        dto.setId(avistamiento.getId());
        dto.setFecha(avistamiento.getFecha());
        dto.setCoordenada(avistamiento.getCoordenada());
        dto.setComentario(avistamiento.getComentario());
        dto.setFotos(avistamiento.getFotos());
        dto.setEnPosesion(avistamiento.isEnPosesion());
        dto.setMascotaId(avistamiento.getMascota().getId());
        dto.setNombreMascota(avistamiento.getMascota().getNombre());
        dto.setUsuarioId(avistamiento.getUsuario().getId());
        dto.setNombreUsuario(avistamiento.getUsuario().getNombre());
        return dto;
    }
}

