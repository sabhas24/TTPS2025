package ttps.grupo2.appmascotas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.repositories.MascotaRepository;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    // Crear nueva mascota
    public Mascota publicarMascota(Mascota mascota) {
        Long idUsuario = mascota.getPublicador().getId();
        Usuario publicador = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        mascota.setPublicador(publicador);
        mascota.setEstado(EstadoMascota.PERDIDO_PROPIO);
        mascota.setFechaPublicacion(LocalDate.now());
        mascota.setHabilitado(true);

        return mascotaRepository.save(mascota);
    }

    // Editar mascota
    public Mascota editarMascota(Long id, Mascota datos) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));

        mascota.setNombre(datos.getNombre());
        mascota.setColor(datos.getColor());
        mascota.setTamanio(datos.getTamanio());
        mascota.setDescripcionExtra(datos.getDescripcionExtra());
        mascota.setCoordenada(datos.getCoordenada());
        mascota.setFotos(datos.getFotos());
        return mascotaRepository.save(mascota);
    }

    // Deshabilitar publicación
    public void deshabilitarMascota(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));
        mascota.deshabilitarPublicacion();
        mascotaRepository.save(mascota);
    }

    // Cambiar estado
    public Mascota cambiarEstado(Long id, EstadoMascota nuevoEstado) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada"));
        mascota.cambiarEstado(nuevoEstado);
        return mascotaRepository.save(mascota);
    }

    //  Listar mascotas perdidas
    public List<Mascota> listarMascotasPerdidas() {
        return mascotaRepository.findByEstado(EstadoMascota.PERDIDO_PROPIO); // o PERDIDO_AJENO si querés incluir ambas
    }

    // Listar mascotas por usuario
    public List<Mascota> listarPorUsuario(Long usuarioId) {
        return mascotaRepository.findByPublicadorId(usuarioId);
    }
}