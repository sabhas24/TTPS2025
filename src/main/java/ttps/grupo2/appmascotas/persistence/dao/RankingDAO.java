package ttps.grupo2.appmascotas.persistence.dao;

import ttps.grupo2.appmascotas.entities.Ranking;

public interface RankingDAO {
    Ranking obtenerRankingTopN(int N);
}

