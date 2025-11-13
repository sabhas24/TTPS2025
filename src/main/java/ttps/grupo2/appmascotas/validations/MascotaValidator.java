package ttps.grupo2.appmascotas.validations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.repositories.MascotaRepository;

import java.util.List;

@Component
public class MascotaValidator {

    private final MascotaRepository mascotaRepository;

    public MascotaValidator(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public void validar(Mascota mascota) {
        validarNombreUnico(mascota.getNombre(), mascota.getPublicador().getId());
        validarTamanio(mascota.getTamanio());
        validarTexto(mascota.getNombre(), "nombre");
        validarTexto(mascota.getColor(), "color");
        validarFotos(mascota.getFotos());
    }

    private void validarNombreUnico(String nombre, Long idPublicador) {
        List<Mascota> mascotas = mascotaRepository.findByPublicadorId(idPublicador);
        boolean duplicado = mascotas.stream().anyMatch(m -> m.getNombre().equalsIgnoreCase(nombre));
        if (duplicado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una mascota con ese nombre para este usuario");
        }
    }

    private void validarTamanio(double tamanio) {
        if (tamanio <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tamaño debe ser positivo");
        }
    }

    private void validarTexto(String texto, String campo) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$";
        if (!texto.matches(regex)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo " + campo + " contiene caracteres inválidos");
        }
    }

    private void validarFotos(List<String> fotos) {
        if (fotos == null || fotos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe incluir al menos una foto");
        }
        boolean formatoIncorrecto = fotos.stream().anyMatch(f -> !f.toLowerCase().endsWith(".jpg"));
        if (formatoIncorrecto) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten fotos en formato .jpg");
        }
    }

    public void validarActualizacion(Mascota nueva, Mascota original) {
        // Validaciones reutilizadas
        validarTamanio(nueva.getTamanio());
        validarTexto(nueva.getNombre(), "nombre");
        validarTexto(nueva.getColor(), "color");
        validarFotos(nueva.getFotos());

        // Validación de nombre único (excepto si es la misma mascota)
        List<Mascota> mascotas = mascotaRepository.findByPublicadorId(original.getPublicador().getId());
        boolean nombreDuplicado = mascotas.stream()
                .anyMatch(m -> !m.getId().equals(original.getId()) &&
                        m.getNombre().equalsIgnoreCase(nueva.getNombre()));
        if (nombreDuplicado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe otra mascota con ese nombre para este usuario");
        }
    }
}
