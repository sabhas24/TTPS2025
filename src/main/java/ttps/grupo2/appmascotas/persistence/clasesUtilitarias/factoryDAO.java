package ttps.grupo2.appmascotas.persistence.clasesUtilitarias;

import ttps.grupo2.appmascotas.persistence.dao.CoordenadaDAO;
import ttps.grupo2.appmascotas.persistence.dao.UsuarioDAO;
import ttps.grupo2.appmascotas.persistence.implementations.CoordenadaDAOHibernateJPA;
import ttps.grupo2.appmascotas.persistence.implementations.UsuarioDAOHibernateJPA;

public class factoryDAO {

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOHibernateJPA();
    }
    public static MascotaDAO getMascotaDAO() {
        return new MascotaDAOHibernateJPA();
    }
    public static PublicacionDAO getPublicacionDAO() {
        return new PublicacionDAOHibernateJPA();
    }
    public static CoordenadaDAO getCoordenadaDAO() {
        return new CoordenadaDAOHibernateJPA();
    }
    public static MedallaDAO getMedallaDAO() {
        return new MedallaDAOHibernateJPA();
    }
}
