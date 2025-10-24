package ttps.grupo2.appmascotas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify that the H2 driver can be loaded successfully
 * for the 'unlp-test' persistence unit.
 */
public class TestH2Connection {

    @Test
    public void testH2DriverLoadsSuccessfully() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // This should not throw "Unable to load class [org.h2.Driver]" anymore
            emf = Persistence.createEntityManagerFactory("unlp-test");
            assertNotNull(emf, "EntityManagerFactory should be created successfully");
            
            em = emf.createEntityManager();
            assertNotNull(em, "EntityManager should be created successfully");
            
            // Verify we can execute a simple query
            em.getTransaction().begin();
            try {
                Object result = em.createNativeQuery("SELECT 1").getSingleResult();
                em.getTransaction().commit();
                assertNotNull(result, "Query should return a result");
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw e;
            }
            
        } catch (Exception e) {
            fail("Failed to create EntityManagerFactory or execute query: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}
