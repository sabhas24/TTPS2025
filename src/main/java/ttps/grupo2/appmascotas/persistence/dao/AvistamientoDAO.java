package ttps.grupo2.appmascotas.persistence.dao;

import ttps.grupo2.appmascotas.entities.Avistamiento;
import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public interface AvistamientoDAO {
    void guardar(Avistamiento avistamiento);
    Avistamiento buscarPorId(Long id);
    List<Avistamiento> listarTodos();
    List<Avistamiento> listarPorMascota(Mascota mascota);
    List<Avistamiento> listarPorUsuario(Usuario usuario);
    void actualizar(Avistamiento avistamiento);
    void eliminar(Long id);
    boolean crearAvistamiento(LocalDateTime fecha, Coordenada coordenada,
                              String comentario, List<String> fotos, boolean enPosesion,
                              Mascota mascota, Usuario usuario);

    boolean actualizarAvistamientoYRecuperarMascota(Long avistamientoId, String nuevoComentario);
}