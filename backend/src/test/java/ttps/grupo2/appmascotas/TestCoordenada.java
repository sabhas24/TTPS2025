package ttps.grupo2.appmascotas;

import org.junit.jupiter.api.*;
import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoordenada {

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
    public void testPersistirMascotaConCoordenada() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Coordenada c = new Coordenada( -34.921, -57.954, "Centro" );
        Mascota m = new Mascota("Firulais", 10.0, "Marrón", "Sin extra", EstadoMascota.PERDIDO_PROPIO,
                LocalDate.now(), new ArrayList<>(), c, null);

        em.persist(m);
        em.getTransaction().commit();

        Long id = m.getId();
        assertNotNull(id);

        Mascota encontrado = em.find(Mascota.class, id);
        assertNotNull(encontrado);
        assertNotNull(encontrado.getCoordenada());
        assertEquals(-34.921, encontrado.getCoordenada().getLatitud());
        assertEquals(-57.954, encontrado.getCoordenada().getLongitud());
        assertEquals("Centro", encontrado.getCoordenada().getBarrio());

        em.close();
    }
}
