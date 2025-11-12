package ttps.grupo2.appmascotas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario editarPerfil(@PathVariable Long id, @RequestBody Usuario datos) {
        return usuarioService.editarPerfil(id, datos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        usuarioService.deshabilitar(id);
    }

    @PostMapping("/login")
    public Usuario login(@RequestParam String email, @RequestParam String contraseña) {
        return usuarioService.login(email, contraseña);
    }
}