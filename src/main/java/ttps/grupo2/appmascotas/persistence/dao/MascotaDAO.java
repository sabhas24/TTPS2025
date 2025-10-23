package ttps.grupo2.appmascotas.persistence.dao;

import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface MascotaDAO {
    void guardar(Mascota mascota);
    Mascota buscarPorId(Long id);
    List<Mascota> listarTodas();
    void actualizar(Mascota mascota);
    boolean eliminar(Long id);

    boolean crearMascota(String nombre, double tamanio, String color,
                         String descripcionExtra, EstadoMascota estado,
                         LocalDate fechaPublicacion, List<String> fotos,
                         Coordenada coordenada, Usuario publicador);


    Mascota buscarPorNombre(String nombre, Locale locale);
}