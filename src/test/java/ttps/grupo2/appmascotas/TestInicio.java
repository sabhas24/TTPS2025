package ttps.grupo2.appmascotas;

import jakarta.persistence.*;

public class TestInicio {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); // fuerza carga del driver

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unlp");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createQuery("SELECT 1").getSingleResult();
        em.getTransaction().commit();

        em.close();
        emf.close();

        System.out.println("Conexión exitosa con la base de datos.");
    }
}