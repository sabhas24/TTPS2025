package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaUpdateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaResponseDTO;
=======
import ttps.grupo2.appmascotas.dto.MascotaRequestDTO;
import ttps.grupo2.appmascotas.dto.MascotaResponseDTO;
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.MascotaRepository;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;
import ttps.grupo2.appmascotas.validations.MascotaValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MascotaValidator mascotaValidator;

    // Crear nueva mascota
<<<<<<< HEAD
    public MascotaResponseDTO publicarMascota(MascotaCreateRequestDTO dto) {
        Usuario publicador = usuarioRepository.findById(dto.getPublicadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Mascota mascota = convertirDesdeCreateDTO(dto, publicador);
=======
    public MascotaResponseDTO publicarMascota(MascotaRequestDTO dto) {
        Usuario publicador = usuarioRepository.findById(dto.getPublicadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Mascota mascota = convertirAMascota(dto, publicador);
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
        mascotaValidator.validar(mascota);

        mascota.setEstado(EstadoMascota.PERDIDO_PROPIO);
        mascota.setFechaPublicacion(LocalDate.now());
        mascota.setHabilitado(true);

        Mascota guardada = mascotaRepository.save(mascota);
<<<<<<< HEAD
        return convertirAResponseDTO(guardada);
    }

    // Actualizar mascota
    public MascotaResponseDTO editarMascota(Long id, MascotaUpdateRequestDTO dto) {
=======
        return convertirAMascotaResponseDTO(guardada);
    }

    // Actualizar mascota
    public MascotaResponseDTO editarMascota(Long id, MascotaRequestDTO dto) {
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        if (!mascota.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No se puede editar una mascota deshabilitada");
        }

        mascotaValidator.validarActualizacion(dto, mascota);

        mascota.setNombre(dto.getNombre());
        mascota.setColor(dto.getColor());
        mascota.setTamanio(dto.getTamanio());
        mascota.setDescripcionExtra(dto.getDescripcionExtra());
        mascota.setCoordenada(dto.getCoordenada());
        mascota.setFotos(dto.getFotos());

        Mascota actualizada = mascotaRepository.save(mascota);
<<<<<<< HEAD
        return convertirAResponseDTO(actualizada);
=======
        return convertirAMascotaResponseDTO(actualizada);
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
    }

    // Deshabilitar mascota
    public void deshabilitarMascota(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        if (!mascota.isHabilitado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La mascota ya está deshabilitada");
        }

        mascota.deshabilitarPublicacion();
        mascotaRepository.save(mascota);
    }

    // Cambiar estado
    public MascotaResponseDTO cambiarEstado(Long id, EstadoMascota nuevoEstado) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        mascota.cambiarEstado(nuevoEstado);
        Mascota actualizada = mascotaRepository.save(mascota);
<<<<<<< HEAD
        return convertirAResponseDTO(actualizada);
=======
        return convertirAMascotaResponseDTO(actualizada);
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
    }

    // Listar mascotas perdidas
    public List<MascotaResponseDTO> listarMascotasPerdidas() {
        return mascotaRepository.findByEstado(EstadoMascota.PERDIDO_PROPIO)
                .stream()
<<<<<<< HEAD
                .map(this::convertirAResponseDTO)
                .toList();
=======
                .map(this::convertirAMascotaResponseDTO)
                .collect(Collectors.toList());
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
    }

    // Listar mascotas por usuario
    public List<MascotaResponseDTO> listarPorUsuario(Long usuarioId) {
        return mascotaRepository.findByPublicadorIdAndHabilitadoTrue(usuarioId)
                .stream()
<<<<<<< HEAD
                .map(this::convertirAResponseDTO)
                .toList();
    }

    // Conversión desde DTO de creación
    private Mascota convertirDesdeCreateDTO(MascotaCreateRequestDTO dto, Usuario publicador) {
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setTamanio(dto.getTamanio());
        mascota.setColor(dto.getColor());
        mascota.setDescripcionExtra(dto.getDescripcionExtra());
        mascota.setFotos(dto.getFotos());
        mascota.setCoordenada(dto.getCoordenada());
        mascota.setPublicador(publicador);
        return mascota;
    }

    // Conversión a DTO de respuesta
    private MascotaResponseDTO convertirAResponseDTO(Mascota mascota) {
=======
                .map(this::convertirAMascotaResponseDTO)
                .collect(Collectors.toList());
    }

    // Conversión entidad → DTO
    private MascotaResponseDTO convertirAMascotaResponseDTO(Mascota mascota) {
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
        MascotaResponseDTO dto = new MascotaResponseDTO();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setColor(mascota.getColor());
        dto.setEstado(mascota.getEstado());
        dto.setFotos(mascota.getFotos());
        dto.setCoordenada(mascota.getCoordenada());
        dto.setNombrePublicador(mascota.getPublicador().getNombre());
        return dto;
<<<<<<< HEAD
=======
    }

    // Conversión DTO → entidad
    private Mascota convertirAMascota(MascotaRequestDTO dto, Usuario publicador) {
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setColor(dto.getColor());
        mascota.setTamanio(dto.getTamanio());
        mascota.setDescripcionExtra(dto.getDescripcionExtra());
        mascota.setFotos(dto.getFotos());
        mascota.setCoordenada(dto.getCoordenada());
        mascota.setPublicador(publicador);
        return mascota;
>>>>>>> 3b7f6a8de1672104aa8df08a06e4f2531f60ccf5
    }
}