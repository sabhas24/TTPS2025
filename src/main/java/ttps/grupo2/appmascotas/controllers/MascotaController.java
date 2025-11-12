package ttps.grupo2.appmascotas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.entities.Mascota;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.services.MascotaService;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mascota publicar(@RequestBody Mascota mascota) {
        return mascotaService.publicarMascota(mascota);
    }

    @PutMapping("/{id}")
    public Mascota editar(@PathVariable Long id, @RequestBody Mascota datos) {
        return mascotaService.editarMascota(id, datos);
    }

    @PatchMapping("/{id}/estado")
    public Mascota cambiarEstado(@PathVariable Long id, @RequestParam EstadoMascota estado) {
        return mascotaService.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        mascotaService.deshabilitarMascota(id);
    }

    @GetMapping("/perdidas")
    public List<Mascota> listarPerdidas() {
        return mascotaService.listarMascotasPerdidas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Mascota> listarPorUsuario(@PathVariable Long usuarioId) {
        return mascotaService.listarPorUsuario(usuarioId);
    }
}