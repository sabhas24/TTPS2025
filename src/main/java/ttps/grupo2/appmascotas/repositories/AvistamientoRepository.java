package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Avistamiento;

import java.util.List;

public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {

    // Listar avistamientos por mascota
    List<Avistamiento> findByMascotaIdAndHabilitadoTrue(Long mascotaId);

    // Listar avistamientos por usuario
    List<Avistamiento> findByUsuarioIdAndHabilitadoTrue(Long usuarioId);

    // Listar todos los avistamientos habilitados
    List<Avistamiento> findByHabilitadoTrue();

    // Listar avistamientos en posesión
    List<Avistamiento> findByEnPosesionTrueAndHabilitadoTrue();
}

