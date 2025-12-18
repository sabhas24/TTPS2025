package ttps.grupo2.appmascotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // ✅ Listar mascotas por estado (ej. perdidas)
    Page<Mascota> findByEstado(EstadoMascota estado, Pageable pageable);

    // ✅ Listar mascotas por ID del publicador (Usuario)
    List<Mascota> findByPublicadorId(Long id);

    // ✅ Listar mascotas habilitadas
    List<Mascota> findByHabilitadoTrue();

    // ✅ Listar mascotas por ciudad del publicador
    List<Mascota> findByPublicadorCiudad(String ciudad);

    // ✅ Listar mascotas por estado y habilitación
    List<Mascota> findByEstadoAndHabilitadoTrue(EstadoMascota estado);

    // ✅ Listar mascotas por estado y habilitación con paginación
    Page<Mascota> findByEstadoAndHabilitadoTrue(EstadoMascota estado, Pageable pageable);

    List<Mascota> findByPublicadorIdAndHabilitadoTrue(Long publicadorId);

    Page<Mascota> findByPublicadorIdAndHabilitadoTrue(Long usuarioId, Pageable pageable);
}