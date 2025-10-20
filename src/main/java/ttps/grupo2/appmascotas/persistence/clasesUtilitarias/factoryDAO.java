package ttps.grupo2.appmascotas.persistence.clasesUtilitarias;

import ttps.grupo2.appmascotas.persistence.dao.UsuarioDAO;
import ttps.grupo2.appmascotas.persistence.dao.UsuarioDAOHibernateJPA;

public class factoryDAO {

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOHibernateJPA();
    }
    public static AdminDao getAdminDAO() {
        return new AdminDAOHibernateJPA();
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
