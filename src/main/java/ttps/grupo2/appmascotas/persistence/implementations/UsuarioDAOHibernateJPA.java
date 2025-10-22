package ttps.grupo2.appmascotas.persistence.implementations;

import jakarta.persistence.EntityManager;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;
import ttps.grupo2.appmascotas.persistence.dao.UsuarioDAO;

import java.util.List;

public class UsuarioDAOHibernateJPA extends GenericDAOHibernateJPA<Usuario> implements UsuarioDAO {
    public UsuarioDAOHibernateJPA() {
        super(Usuario.class);
    }

    @Override
    public Usuario getByEmail(String mail) {
        EntityManager em = EMF.getEMF().createEntityManager();
        Usuario usr;
        try {
            usr = (Usuario) em.createQuery("SELECT m FROM " +
                            this.getPersistentClass().getSimpleName() + " m WHERE m.email = :email")
                    .setParameter("email", mail).getSingleResult();
        } catch (Exception e) {
            usr = null;
        } finally {
            em.close();
        }
        return usr;
    }

    @Override
    public Usuario buscarPorEmailYContraseña(String mail, String contraseña) {
        EntityManager em = EMF.getEMF().createEntityManager();
        Usuario usr;
        try {
            usr = (Usuario) em.createQuery("SELECT m FROM " +
                            this.getPersistentClass().getSimpleName() + " m WHERE m.email = :email and m.contraseña = :contraseña")
                    .setParameter("contraseña", contraseña)
                    .setParameter("email", mail).getSingleResult();
        } catch (Exception e) {
            usr = null;
        } finally {
            em.close();
        }
        return usr;
    }

    @Override
    public List<Usuario> getTopNUsuarios(int N) {
        EntityManager em = EMF.getEMF().createEntityManager();
        List<Usuario> usuarios;
        try {
            usuarios = em.createQuery("SELECT m FROM " +
                            this.getPersistentClass().getSimpleName() + " m ORDER BY m.puntos DESC", Usuario.class)
                    .setMaxResults(N)
                    .getResultList();
        } catch (Exception e) {
            usuarios = null;
        } finally {
            em.close();
        }
        return usuarios;
    }
}
