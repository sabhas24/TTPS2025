package ttps.grupo2.appmascotas.persistence.clasesUtilitarias;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class EMF {
    private static EntityManagerFactory em = null;

    private EMF() {}

    public static synchronized EntityManagerFactory getEMF(){
        if (em == null) {
            String pu = System.getProperty("persistence.unit", "unlp");
            try {
                em = Persistence.createEntityManagerFactory(pu);
            } catch (PersistenceException e) {
                String msg = "Error al crear EntityManagerFactory for unit '"+pu+"': "+e.getMessage();
                System.err.println(msg);
                e.printStackTrace();
                throw new IllegalStateException(msg, e);
            }
            if (em == null) {
                String msg = "EntityManagerFactory es null para la unidad: " + pu;
                throw new IllegalStateException(msg);
            }
        }
        return em;
    }

    public static synchronized void close(){
        if (em != null && em.isOpen()){
            em.close();
            em = null;
        }
    }
}
