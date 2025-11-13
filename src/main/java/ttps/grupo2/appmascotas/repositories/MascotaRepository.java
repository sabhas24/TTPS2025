package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // ✅ Listar mascotas por estado (ej. perdidas)
    List<Mascota> findByEstado(EstadoMascota estado);

    // ✅ Listar mascotas por ID del publicador (Usuario)
    List<Mascota> findByPublicadorId(Long id);

    // ✅ Listar mascotas habilitadas
    List<Mascota> findByHabilitadoTrue();

    // ✅ Listar mascotas por ciudad del publicador
    List<Mascota> findByPublicadorCiudad(String ciudad);

    // ✅ Listar mascotas por estado y habilitación
    List<Mascota> findByEstadoAndHabilitadoTrue(EstadoMascota estado);

    List<Mascota> findByPublicadorIdAndHabilitadoTrue(Long publicadorId);
}