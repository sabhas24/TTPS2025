package ttps.grupo2.appmascotas.entities;

import java.util.List;
import java.util.ArrayList;

public class Ranking {

    private List<Usuario> usuariosOrdenados;

    // Constructor vacío
    public Ranking() {
        this.usuariosOrdenados = new ArrayList<>();
    }

    // Constructor con lista
    public Ranking(List<Usuario> usuariosOrdenados) {
        this.usuariosOrdenados = usuariosOrdenados;
    }

    // Getter
    public List<Usuario> getUsuariosOrdenados() {
        return usuariosOrdenados;
    }

    // Setter
    public void setUsuariosOrdenados(List<Usuario> usuariosOrdenados) {
        this.usuariosOrdenados = usuariosOrdenados;
    }

    // Método para agregar un usuario al ranking
    public void agregarUsuario(Usuario usuario) {
        this.usuariosOrdenados.add(usuario);
    }

    // Método para mostrar el ranking
    @Override
    public String toString() {
        return "Ranking{" +
                "usuariosOrdenados=" + usuariosOrdenados +
                '}';
    }
}
