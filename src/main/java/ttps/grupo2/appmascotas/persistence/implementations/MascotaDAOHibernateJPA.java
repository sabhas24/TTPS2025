package ttps.grupo2.appmascotas.persistence.implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import ttps.grupo2.appmascotas.entities.Coordenada;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.persistence.dao.MascotaDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class MascotaDAOHibernateJPA implements MascotaDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("unlp");

    @Override
    public void guardar(Mascota mascota) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(mascota);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Mascota buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Mascota mascota = em.find(Mascota.class, id);

        // Forzar la carga de fotos dentro de la sesión activa
        if (mascota != null) {
            mascota.getFotos().size(); // accede para inicializar
        }

        em.close();
        return mascota;
    }


    @Override
    public List<Mascota> listarTodas() {
        EntityManager em = emf.createEntityManager();

        List<Mascota> mascotas = em.createQuery(
                        "SELECT m FROM Mascota m WHERE m.estado NOT IN (:estado1, :estado2)", Mascota.class)
                .setParameter("estado1", EstadoMascota.ADOPTADO)
                .setParameter("estado2", EstadoMascota.EN_POSESION)
                .getResultList();

        em.close();
        return mascotas;
    }

    @Override
    public void actualizar(Mascota mascota) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(mascota);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        Mascota mascota = em.find(Mascota.class, id);

        if (mascota == null) {
            em.close();
            return false; // No se encontró la mascota
        }

        try {
            em.getTransaction().begin();

            // Validar estado antes de eliminar
            if (mascota.getEstado() == EstadoMascota.ADOPTADO || mascota.getEstado() == EstadoMascota.EN_POSESION) {
                mascota.setHabilitado(false); // Deshabilitar en lugar de eliminar
                em.merge(mascota);
            } else {
                em.remove(mascota); // Eliminar físicamente
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


    @Override
    public boolean crearMascota(String nombre, double tamanio, String color, String descripcionExtra,
                                EstadoMascota estado, LocalDate fechaPublicacion, List<String> fotos,
                                Coordenada coordenada, Usuario publicador) {
        try {
            Mascota mascota = new Mascota(nombre, tamanio, color, descripcionExtra,
                    estado, fechaPublicacion, fotos, coordenada, publicador);

            guardar(mascota);

            Mascota recuperada = buscarPorId(mascota.getId());
            return recuperada != null &&
                    recuperada.getNombre().equals(nombre) &&
                    recuperada.getEstado() == estado &&
                    recuperada.getFotos().equals(fotos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Mascota buscarPorNombre(String nombre, Locale locale) {
        EntityManager em = emf.createEntityManager();
        Mascota resultado = null;

        try {
            resultado = em.createQuery(
                            "SELECT m FROM Mascota m WHERE m.nombre = :nombre", Mascota.class)
                    .setParameter("nombre", nombre)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            // No se encontró ninguna mascota con ese nombre
        } finally {
            em.close();
        }

        return resultado;
    }


}

