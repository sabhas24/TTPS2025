package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioRegisterResponseDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioUpdateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.UsuariosDTOs.UsuarioResponseDTO;
import ttps.grupo2.appmascotas.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema si el email no está registrado. Devuelve los datos del usuario creado.")
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class)))
        @ApiResponse(responseCode = "409", description = "El email ya está registrado")
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        @PostMapping("/registrar")
        @ResponseStatus(HttpStatus.CREATED)
        public UsuarioRegisterResponseDTO registrar(@RequestBody UsuarioCreateRequestDTO dto) {
            return usuarioService.registrar(dto);
        }

        @Operation(summary = "Editar perfil de usuario", description = "Actualiza los datos personales del usuario identificado por su ID. Solo se modifican campos visibles del perfil.")
        @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class)))
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        @ApiResponse(responseCode = "401", description = "No autorizado para editar este perfil")
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
        @PutMapping("/{id}")
        public UsuarioResponseDTO editarPerfil(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO dto) {
                return usuarioService.editarPerfil(id, dto);
        }

        @Operation(summary = "Obtener perfil", description = "Recupera los datos del usuario identificado por su ID.")
        @ApiResponse(responseCode = "200", description = "Datos del usuario recuperados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class)))
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        @ApiResponse(responseCode = "401", description = "No autorizado")
        @GetMapping("/{id}")
        public UsuarioResponseDTO obtenerDatos(@PathVariable Long id) {
                return usuarioService.buscarPorId(id);
        }

        @Operation(summary = "Deshabilitar usuario", description = "Marca al usuario como deshabilitado. No elimina el registro de la base de datos.")
        @ApiResponse(responseCode = "204", description = "Usuario deshabilitado correctamente")
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        @ApiResponse(responseCode = "401", description = "No autorizado para deshabilitar este usuario")
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deshabilitar(@PathVariable Long id) {
                usuarioService.deshabilitar(id);
        }

        @Operation(summary = "obtener todos los usuarios", description = "Recupera los datos de todos los usuarios registrados.")
        @ApiResponse(responseCode = "200", description = "Datos de los usuarios recuperados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class)))
        @GetMapping("/")
        public Iterable<UsuarioResponseDTO> obtenerTodos() {
                return usuarioService.buscarTodos();
        }
}