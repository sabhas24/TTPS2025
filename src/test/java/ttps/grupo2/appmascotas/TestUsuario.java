package ttps.grupo2.appmascotas;

import org.junit.jupiter.api.*;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.entities.TipoUsuario;
import ttps.grupo2.appmascotas.entities.Medalla;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestUsuario {

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void init() {
        System.setProperty("persistence.unit", "unlp-test");
        emf = EMF.getEMF();
    }

    @AfterAll
    public static void close() {
        EMF.close();
    }

    @Test
    public void testCrearYPersistirUsuario() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Usuario u = new Usuario("Juan", "Pérez", "juan@example.com", "clave", "1234", "Centro", "La Plata", null, TipoUsuario.USUARIO);
        em.persist(u);

        em.getTransaction().commit();
        Long id = u.getId();
        assertNotNull(id);
        em.close();

        // Recuperar con un nuevo EntityManager para simular fetch desde BD
        EntityManager em2 = emf.createEntityManager();
        Usuario encontrado = em2.find(Usuario.class, id);
        assertNotNull(encontrado);
        assertEquals("juan@example.com", encontrado.getEmail());
        assertEquals("Juan", encontrado.getNombre());
        em2.close();
    }

    @Test
    public void testSumarPuntosYEditarPerfil() {
        Usuario u = new Usuario("Ana", "Gomez", "ana@example.com", "pwd", "9999", "Norte", "La Plata", null, TipoUsuario.USUARIO);
        assertEquals(0, u.getPuntos());

        u.sumarPuntos(30);
        assertEquals(30, u.getPuntos());

        u.editarPerfil("AnaMaria","Gomez","8888","Norte","La Plata", null);
        assertEquals("AnaMaria", u.getNombre());
        assertEquals("8888", u.getTelefono());

        u.deshabilitarUsuario();
        assertFalse(u.isHabilitado());
    }

    @Test
    public void testAgregarMedalla() {
        Usuario u = new Usuario("Luis","Lopez","luis@example.com","pw","5555","Sur","La Plata",null, TipoUsuario.USUARIO);
        Medalla m = new Medalla(null, "Helper", "Ayudó a alguien", "icon.png");

        u.agregarMedalla(m);
        assertNotNull(u.getMedallas());
        assertEquals(1, u.getMedallas().size());
        Medalla primera = u.getMedallas().stream().findFirst().orElse(null);
        assertNotNull(primera);
        assertEquals("Helper", primera.getNombre());
    }
}
