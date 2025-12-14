package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Avistamiento;
import java.util.List;

public interface AvistamientoRepository extends JpaRepository<Avistamiento, Long> {
    List<Avistamiento> findByMascotaId(Long mascotaId);
}
