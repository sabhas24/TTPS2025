package ttps.grupo2.appmascotas.persistence.clasesUtilitarias;

import ttps.grupo2.appmascotas.persistence.dao.AvistamientoDAO;
import ttps.grupo2.appmascotas.persistence.dao.CoordenadaDAO;
import ttps.grupo2.appmascotas.persistence.dao.MascotaDAO;
import ttps.grupo2.appmascotas.persistence.dao.UsuarioDAO;
import ttps.grupo2.appmascotas.persistence.implementations.AvistamientoDAOHibernateJPA;
import ttps.grupo2.appmascotas.persistence.implementations.CoordenadaDAOHibernateJPA;
import ttps.grupo2.appmascotas.persistence.implementations.MascotaDAOHibernateJPA;
import ttps.grupo2.appmascotas.persistence.implementations.UsuarioDAOHibernateJPA;

public class factoryDAO {

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOHibernateJPA();
    }
    public static CoordenadaDAO getCoordenadaDAO() {
        return new CoordenadaDAOHibernateJPA();
    }
    public static MascotaDAO getMascotaDAO() { return new MascotaDAOHibernateJPA(); }
    public static AvistamientoDAO getAvistamientoDAO() { return new AvistamientoDAOHibernateJPA(); }

}
