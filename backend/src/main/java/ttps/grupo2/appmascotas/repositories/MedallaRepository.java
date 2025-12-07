package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Medalla;

public interface MedallaRepository extends JpaRepository<Medalla, Long> {
    public Medalla findByNombre(String nombre);
}
