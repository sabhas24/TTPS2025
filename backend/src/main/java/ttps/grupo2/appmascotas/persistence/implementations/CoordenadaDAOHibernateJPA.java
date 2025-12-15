package ttps.grupo2.appmascotas.persistence.implementations;

import ttps.grupo2.appmascotas.entities.Coordenada;

import ttps.grupo2.appmascotas.persistence.dao.CoordenadaDAO;

public class CoordenadaDAOHibernateJPA extends GenericDAOHibernateJPA<Coordenada> implements CoordenadaDAO {
    public CoordenadaDAOHibernateJPA() {
        super(Coordenada.class);
    }
}