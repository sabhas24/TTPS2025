package ttps.grupo2.appmascotas.persistence.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ttps.grupo2.appmascotas.entities.*;
import ttps.grupo2.appmascotas.persistence.dao.AvistamientoDAO;
import ttps.grupo2.appmascotas.persistence.clasesUtilitarias.EMF;

import java.time.LocalDateTime;
import java.util.List;

public class AvistamientoDAOHibernateJPA implements AvistamientoDAO {

    @Override
    public void guardar(Avistamiento avistamiento) {
        EntityManager em = EMF.getEMF().createEntityManager();
        em.getTransaction().begin();
        em.persist(avistamiento);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Avistamiento buscarPorId(Long id) {
        EntityManager em = EMF.getEMF().createEntityManager();
        Avistamiento a = em.find(Avistamiento.class, id);
        em.close();
        return a;
    }

    @Override
    public List<Avistamiento> listarTodos() {
        EntityManager em = EMF.getEMF().createEntityManager();
        List<Avistamiento> lista = em.createQuery("SELECT a FROM Avistamiento a", Avistamiento.class).getResultList();
        em.close();
        return lista;
    }

    @Override
    public List<Avistamiento> listarPorMascota(Mascota mascota) {
        EntityManager em = EMF.getEMF().createEntityManager();
        List<Avistamiento> lista = em.createQuery(
                        "SELECT a FROM Avistamiento a WHERE a.mascota = :mascota", Avistamiento.class)
                .setParameter("mascota", mascota)
                .getResultList();
        em.close();
        return lista;
    }

    @Override
    public List<Avistamiento> listarPorUsuario(Usuario usuario) {
        EntityManager em = EMF.getEMF().createEntityManager();
        List<Avistamiento> lista = em.createQuery(
                        "SELECT a FROM Avistamiento a WHERE a.usuario = :usuario", Avistamiento.class)
                .setParameter("usuario", usuario)
                .getResultList();
        em.close();
        return lista;
    }

    @Override
    public void actualizar(Avistamiento avistamiento) {
        EntityManager em = EMF.getEMF().createEntityManager();
        em.getTransaction().begin();
        em.merge(avistamiento);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            Avistamiento a = em.find(Avistamiento.class, id);
            if (a == null) return false;

            em.getTransaction().begin();
            a.setHabilitado(false);
            em.merge(a);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }


    @Override
    public boolean crearAvistamiento(LocalDateTime fecha, Coordenada coordenada,
                                     String comentario, List<String> fotos, boolean enPosesion,
                                     Mascota mascota, Usuario usuario) {
        EntityManager em = EMF.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            // Reatachar entidades para que estén en estado managed
            Mascota mascotaManaged = em.find(Mascota.class, mascota.getId());
            Usuario usuarioManaged = em.find(Usuario.class, usuario.getId());

            Avistamiento avistamiento = new Avistamiento(fecha, coordenada, comentario, fotos, enPosesion, mascotaManaged, usuarioManaged);

            // Persistir el avistamiento
            em.persist(avistamiento);

            // Actualizar relaciones
            mascotaManaged.agregarAvistamiento(avistamiento);
            usuarioManaged.agregarAvistamiento(avistamiento);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean actualizarComentarioYRecuperarSiPerdida(Long avistamientoId, String nuevoComentario) {
        EntityManager em = EMF.getEMF().createEntityManager();

        try {
            Avistamiento avistamiento = em.find(Avistamiento.class, avistamientoId);
            if (avistamiento == null) return false;

            Mascota mascota = avistamiento.getMascota();
            if (mascota == null) return false;

            em.getTransaction().begin();

            // Actualizar comentario del avistamiento
            avistamiento.setComentario(nuevoComentario);

            // Marcar como en posesión
            avistamiento.setEnPosesion(true);
            em.merge(avistamiento);

            // Cambiar estado de la mascota solo si está perdida
            EstadoMascota estadoActual = mascota.getEstado();
            if (estadoActual == EstadoMascota.PERDIDO_PROPIO || estadoActual == EstadoMascota.PERDIDO_AJENO) {
                mascota.cambiarEstado(EstadoMascota.RECUPERADO);
                em.merge(mascota);
            }

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

}