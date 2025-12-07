package ttps.grupo2.appmascotas;

import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.persistence.dao.MascotaDAO;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.factoryDAO;

import java.time.LocalDate;
import java.util.List;

public class TestMascotaDAO {
    public static void main(String[] args) {
        MascotaDAO dao = factoryDAO.getMascotaDAO();

        //Creacion
        boolean ok1 = dao.crearMascota("Luna", 0.5, "Negro", "Muy juguetona",
                EstadoMascota.PERDIDO_PROPIO, LocalDate.now(), List.of("foto1.jpg"),
                new Coordenada(-34.921, -57.954, "Centro"), null);

        boolean ok2 = dao.crearMascota("Max", 0.7, "Marrón", "Tiene collar rojo",
                EstadoMascota.PERDIDO_AJENO, LocalDate.now(), List.of("foto2.jpg"),
                new Coordenada(-34.920, -57.950, "La Loma"), null);

        boolean ok3 = dao.crearMascota("Nina", 0.4, "Blanco", "Muy tranquila",
                EstadoMascota.EN_ADOPCION, LocalDate.now(), List.of("foto3.jpg"),
                new Coordenada(-34.922, -57.960, "Tolosa"), null);

        assert ok1 : "Falló la creación de Luna";
        assert ok2 : "Falló la creación de Max";
        assert ok3 : "Falló la creación de Nina";

        // busqueda y actualizacon
        Mascota nina = dao.buscarPorNombre("Nina", null);
        assert nina != null : "No se encontró a Nina";

        nina.cambiarEstado(EstadoMascota.ADOPTADO);
        dao.actualizar(nina);

        // elimincaion
        boolean eliminada = dao.eliminar(nina.getId());
        assert eliminada : "Falló la eliminación lógica de Nina";

        Mascota ninaPost = dao.buscarPorId(nina.getId());
        assert ninaPost != null : "Nina fue eliminada físicamente (no debería)";
        assert !ninaPost.isHabilitado() : "Nina sigue habilitada (debería estar deshabilitada)";
        assert ninaPost.getEstado() == EstadoMascota.ADOPTADO : "Estado incorrecto después de eliminar";

        // Mensaje final si todo pasó correctamente
        System.out.println("Todos los tests pasaron correctamente.");
    }
}