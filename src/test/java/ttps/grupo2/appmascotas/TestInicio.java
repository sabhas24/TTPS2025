package ttps.grupo2.appmascotas;

import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TestInicio {
    public static void main(String[] args) throws Exception {
        // Forzar carga del driver si fuera necesario
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Usar EMF centralizado
        EntityManagerFactory emf = EMF.getEMF();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createQuery("SELECT 1").getSingleResult();
        em.getTransaction().commit();

        em.close();
        EMF.close();

        System.out.println("Conexión exitosa con la base de datos.");
    }
}