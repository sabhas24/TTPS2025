package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ttps.grupo2.appmascotas.DTOs.Avistamientos.*;
import ttps.grupo2.appmascotas.repositories.AvistamientoRepository;

import ttps.grupo2.appmascotas.entities.Avistamiento;
import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Usuario;

import ttps.grupo2.appmascotas.repositories.MascotaRepository;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvistamientoService {
    @Autowired
    private AvistamientoRepository avistamientoRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public AvistamientoResponseDTO crearAvistamiento(AvistamientoCreateRequestDTO dto) {

        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Coordenada coordenada = new Coordenada(dto.getCoordenada().getLatitud(), dto.getCoordenada().getLongitud(),
                dto.getCoordenada().getBarrio());

        Avistamiento avistamiento = new Avistamiento();
        avistamiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        avistamiento.setCoordenada(coordenada);
        avistamiento.setComentario(dto.getComentario());
        avistamiento.setFotos(dto.getFoto());
        avistamiento.setEnPosesion(dto.getEnPosesion());
        avistamiento.setMascota(mascota);
        avistamiento.setUsuario(usuario);
        avistamiento.setHabilitado(true);

        Avistamiento guardado = avistamientoRepository.save(avistamiento);
        return convertirAResponseDTO(guardado);
    }

    private AvistamientoResponseDTO convertirAResponseDTO(Avistamiento av) {
        AvistamientoResponseDTO dto = new AvistamientoResponseDTO();
        dto.setId(av.getId());
        dto.setFecha(av.getFecha());
        dto.setComentario(av.getComentario());
        dto.setFotos(av.getFotos());
        dto.setEnPosesion(av.isEnPosesion());
        dto.setMascotaId(av.getMascota() != null ? av.getMascota().getId() : null);
        dto.setUsuarioId(av.getUsuario() != null ? av.getUsuario().getId() : null);
        dto.setHabilitado(av.isHabilitado());
        if (av.getCoordenada() != null) {
            Coordenada coord = new Coordenada(av.getCoordenada().getLatitud(), av.getCoordenada().getLongitud(),
                    av.getCoordenada().getBarrio());
            dto.setCoordenada(coord);
        }
        return dto;
    }

    public List<AvistamientoResponseDTO> getAvistamientosPorMascota(Long mascotaId) {
        List<Avistamiento> avistamientos = avistamientoRepository.findByMascotaId(mascotaId);
        return avistamientos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public AvistamientoResponseDTO actualizarAvistamiento(Long id, AvistamientoUpdateRequestDTO dto) {
        Avistamiento avistamiento = avistamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avistamiento no encontrado"));

        if (dto.getComentario() != null) {
            avistamiento.setComentario(dto.getComentario());
        }
        if (dto.getFotos() != null) {
            avistamiento.setFotos(dto.getFotos());
        }
        if (dto.getEnPosesion() != null) {
            avistamiento.setEnPosesion(dto.getEnPosesion());
        }
        if (dto.getCoordenada() != null) {
            Coordenada coord = new Coordenada(dto.getCoordenada().getLatitud(), dto.getCoordenada().getLongitud(),
                    dto.getCoordenada().getBarrio());
            avistamiento.setCoordenada(coord);
        }
        if (dto.getHabilitado() != null) {
            avistamiento.setHabilitado(dto.getHabilitado());
        }

        Avistamiento actualizado = avistamientoRepository.save(avistamiento);
        return convertirAResponseDTO(actualizado);
    }

    public List<AvistamientoResponseDTO> listarTodos() {
        return avistamientoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public AvistamientoResponseDTO buscarPorId(Long id) {
        Avistamiento avistamiento = avistamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avistamiento no encontrado"));
        return convertirAResponseDTO(avistamiento);
    }

    public void deshabilitar(Long id) {
        Avistamiento avistamiento = avistamientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avistamiento no encontrado"));
        avistamiento.setHabilitado(false);
        avistamientoRepository.save(avistamiento);
    }
}
