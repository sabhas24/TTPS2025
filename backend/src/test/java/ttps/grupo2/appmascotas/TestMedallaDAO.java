package ttps.grupo2.appmascotas;

import org.junit.jupiter.api.*;
import ttps.grupo2.appmascotas.entities.Medalla;
import ttps.grupo2.appmascotas.persistence.implementations.MedallaDAOHibernateJPA;
import ttps.grupo2.appmascotas.persistence.dao.MedallaDAO;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestMedallaDAO {

    private static EntityManagerFactory emf;
    private MedallaDAO medallaDAO;

    @BeforeAll
    public static void init() {
        System.setProperty("persistence.unit", "unlp-test");
        emf = EMF.getEMF();
    }

    @BeforeEach
    public void setUp() {
        medallaDAO = new MedallaDAOHibernateJPA();
    }

    @AfterAll
    public static void tearDown() {
        EMF.close();
    }

    @Test
    public void testCrearYBuscarMedallaPorNombre() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Medalla med = new Medalla(null, "Salvador", "Ayudó a encontrar 5 mascotas", "icon_salvador.png");
        em.persist(med);

        em.getTransaction().commit();
        em.close();

        Medalla recuperada = medallaDAO.getByNombre("Salvador");
        assertNotNull(recuperada);
        assertEquals("Salvador", recuperada.getNombre());
        assertEquals("Ayudó a encontrar 5 mascotas", recuperada.getDescripcion());
    }

    @Test
    public void testPersistMedallaViajandoPorDAO() {
        Medalla med2 = new Medalla(null, "Colaborador", "Reportó 3 avistamientos útiles", "icon_colaborador.png");
        Medalla persistida = medallaDAO.persist(med2);
        assertNotNull(persistida);
        assertNotNull(persistida.getId());

        Medalla buscada = medallaDAO.getByNombre("Colaborador");
        assertNotNull(buscada);
        assertEquals("Colaborador", buscada.getNombre());
    }
}
