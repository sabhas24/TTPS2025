package ttps.grupo2.appmascotas;

import org.junit.jupiter.api.*;
import ttps.grupo2.appmascotas.entities.Ranking;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.entities.TipoUsuario;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.factoryDAO;
import ttps.grupo2.appmascotas.persistence.dao.RankingDAO;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestRankingDAO {

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void init() {
        System.setProperty("persistence.unit", "unlp-test");
        emf = EMF.getEMF();
    }

    @AfterAll
    public static void tearDown() {
        EMF.close();
    }

    @Test
    public void testObtenerRankingTopN() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Usuario u1 = new Usuario("Alice", "A", "alice@example.com", "pw1", "1111", "Centro", "La Plata", null, TipoUsuario.USUARIO);
        u1.setPuntos(50);
        em.persist(u1);

        Usuario u2 = new Usuario("Bob", "B", "bob@example.com", "pw2", "2222", "Norte", "La Plata", null, TipoUsuario.USUARIO);
        u2.setPuntos(100);
        em.persist(u2);

        Usuario u3 = new Usuario("Carol", "C", "carol@example.com", "pw3", "3333", "Sur", "La Plata", null, TipoUsuario.USUARIO);
        u3.setPuntos(75);
        em.persist(u3);

        em.getTransaction().commit();
        em.close();

        RankingDAO rankingDAO = factoryDAO.getRankingDAO();
        Ranking rankingTop2 = rankingDAO.obtenerRankingTopN(2);

        assertNotNull(rankingTop2);
        assertEquals(2, rankingTop2.getUsuariosOrdenados().size(), "Debe devolver 2 usuarios");

        // El primero debe ser Bob (100 puntos), segundo Carol (75)
        assertEquals("Bob", rankingTop2.getUsuariosOrdenados().get(0).getNombre());
        assertEquals(100, rankingTop2.getUsuariosOrdenados().get(0).getPuntos());

        assertEquals("Carol", rankingTop2.getUsuariosOrdenados().get(1).getNombre());
        assertEquals(75, rankingTop2.getUsuariosOrdenados().get(1).getPuntos());
    }
}
