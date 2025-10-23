package ttps.grupo2.appmascotas;

import org.junit.jupiter.api.*;
import ttps.grupo2.appmascotas.entities.*;
import ttps.grupo2.appmascotas.persistence.dao.AvistamientoDAO;
import ttps.grupo2.appmascotas.persistence.implementations.AvistamientoDAOHibernateJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestAvistamientoDAO {

    private static EntityManagerFactory emf;
    private AvistamientoDAO dao;

    @BeforeAll
    public static void init() {
        emf = Persistence.createEntityManagerFactory("unlp");
    }

    @BeforeEach
    public void setUp() {
        dao = new AvistamientoDAOHibernateJPA();
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) emf.close();
    }

    @Test
    public void testCrearAvistamiento() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Crear usuario
        Usuario usuario = new Usuario("Juan", "Pérez", "juan@example.com", "1234", "2211234567", "Centro", "La Plata", null, TipoUsuario.USUARIO);
        em.persist(usuario);

        // Coordenada de la mascota
        Coordenada coordenadaMascota = new Coordenada(-34.921, -57.954, "Centro");

        // Crear mascota
        Mascota mascota = new Mascota(
                "Firulais", 0.5, "Marrón",
                "Tiene collar rojo", EstadoMascota.PERDIDO_PROPIO,
                LocalDate.now(), new ArrayList<>(List.of("foto1.jpg")),
                coordenadaMascota, usuario
        );
        em.persist(mascota);

        em.getTransaction().commit();
        em.close();

        // Recuperar entidades en estado managed
        em = emf.createEntityManager();
        Mascota mascotaRef = em.find(Mascota.class, mascota.getId());
        Usuario usuarioRef = em.find(Usuario.class, usuario.getId());
        em.close();

        // Coordenada del avistamiento
        Coordenada coordenadaAvistamiento = new Coordenada(-34.920, -57.950, "Plaza San Martín");

        // Ejecutar método DAO
        boolean resultado = dao.crearAvistamiento(
                LocalDateTime.now(),
                coordenadaAvistamiento,
                "Visto cerca de la plaza",
                new ArrayList<>(List.of("foto2.jpg", "foto3.jpg")),
                false,
                mascotaRef,
                usuarioRef
        );

        assertTrue(resultado, "El método debe retornar true");

        // Verificar que se guardó el avistamiento
        em = emf.createEntityManager();
        List<Avistamiento> avistamientos = em.createQuery("SELECT a FROM Avistamiento a", Avistamiento.class).getResultList();
        assertEquals(1, avistamientos.size());

        Avistamiento a = avistamientos.get(0);
        assertEquals("Visto cerca de la plaza", a.getComentario());
        assertEquals("Firulais", a.getMascota().getNombre());
        assertEquals("Juan", a.getUsuario().getNombre());
        assertEquals("Plaza San Martín", a.getCoordenada().getBarrio());

        // Verificar que la mascota tiene el avistamiento (usando JOIN FETCH)
        Mascota m = em.createQuery(
                        "SELECT m FROM Mascota m LEFT JOIN FETCH m.avistamientos WHERE m.id = :id", Mascota.class)
                .setParameter("id", mascota.getId())
                .getSingleResult();

        assertNotNull(m.getAvistamientos());
        assertTrue(m.getAvistamientos().contains(a), "La mascota debe tener el avistamiento asociado");

        // Verificar que el usuario también lo tiene (usando JOIN FETCH)
        Usuario u = em.createQuery(
                        "SELECT u FROM Usuario u LEFT JOIN FETCH u.avistamientosReportados WHERE u.id = :id", Usuario.class)
                .setParameter("id", usuario.getId())
                .getSingleResult();

        assertNotNull(u.getAvistamientosReportados());
        assertTrue(u.getAvistamientosReportados().contains(a), "El usuario debe tener el avistamiento reportado");
        System.out.println("✅ Test testCrearAvistamiento ejecutado correctamente.");
        em.close();
    }
}