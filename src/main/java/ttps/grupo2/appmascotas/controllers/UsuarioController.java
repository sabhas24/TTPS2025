package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioUpdateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioLoginRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioResponseDTO;
import ttps.grupo2.appmascotas.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema si el email no está registrado. Devuelve los datos del usuario creado."
    )
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO registrar(@RequestBody UsuarioCreateRequestDTO dto) {
        return usuarioService.registrar(dto);
    }

    @Operation(
            summary = "Editar perfil de usuario",
            description = "Actualiza los datos personales del usuario identificado por su ID. Solo se modifican campos visibles del perfil."
    )
    @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @PutMapping("/{id}")
    public UsuarioResponseDTO editarPerfil(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO dto) {
        return usuarioService.editarPerfil(id, dto);
    }

    @Operation(
            summary = "Deshabilitar usuario",
            description = "Marca al usuario como deshabilitado. No elimina el registro de la base de datos."
    )
    @ApiResponse(responseCode = "204", description = "Usuario deshabilitado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        usuarioService.deshabilitar(id);
    }

    @Operation(
            summary = "Login de usuario",
            description = "Verifica las credenciales del usuario. Si son válidas y el usuario está habilitado, devuelve sus datos."
    )
    @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestBody UsuarioLoginRequestDTO dto) {
        return usuarioService.login(dto);
    }
}