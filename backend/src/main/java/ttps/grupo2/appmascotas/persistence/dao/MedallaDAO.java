package ttps.grupo2.appmascotas.persistence.dao;

import ttps.grupo2.appmascotas.entities.Medalla;

public interface MedallaDAO extends GenericDAO<Medalla> {
    Medalla getByNombre(String nombre);
}

