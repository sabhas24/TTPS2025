package ttps.grupo2.appmascotas.persistence.implementations;

import ttps.grupo2.appmascotas.entities.Ranking;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.persistence.dao.RankingDAO;

import java.util.List;

public class RankingDAOHibernateJPA implements RankingDAO {

    @Override
    public Ranking obtenerRankingTopN(int N) {
        // Evitar dependencia circular con factoryDAO instanciando directamente el DAO concreto
        UsuarioDAOHibernateJPA usuarioDAO = new UsuarioDAOHibernateJPA();
        List<Usuario> usuarios = usuarioDAO.getTopNUsuarios(N);
        return new Ranking(usuarios);
    }
}
