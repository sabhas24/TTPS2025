package ttps.grupo2.appmascotas.persistence.implementations;

import ttps.grupo2.appmascotas.entities.Medalla;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;
import ttps.grupo2.appmascotas.persistence.dao.MedallaDAO;

import jakarta.persistence.EntityManager;


public class MedallaDAOHibernateJPA extends GenericDAOHibernateJPA<Medalla> implements MedallaDAO {

    public MedallaDAOHibernateJPA() {
        super(Medalla.class);
    }

    @Override
    public Medalla getByNombre(String nombre) {
        try (EntityManager em = EMF.getEMF().createEntityManager()) {
            return em.createQuery("SELECT m FROM Medalla m WHERE m.nombre = :nombre", Medalla.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
