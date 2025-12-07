package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.DTOs.AvistamientosDTOs.AvistamientoCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.AvistamientosDTOs.AvistamientoResponseDTO;
import ttps.grupo2.appmascotas.services.AvistamientoService;

import java.util.List;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

    private final AvistamientoService avistamientoService;

    // Constructor injection
    public AvistamientoController(AvistamientoService avistamientoService) {
        this.avistamientoService = avistamientoService;
    }

    @Operation(
            summary = "Crear un nuevo avistamiento",
            description = "Registra un nuevo avistamiento de una mascota. Incluye ubicación, comentario y fotos opcionales."
    )
    @ApiResponse(responseCode = "201", description = "Avistamiento creado exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AvistamientoResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvistamientoResponseDTO crear(@RequestBody AvistamientoCreateRequestDTO dto) {
        return avistamientoService.crearAvistamiento(dto);
    }

    @Operation(
            summary = "Listar todos los avistamientos",
            description = "Devuelve todos los avistamientos habilitados del sistema."
    )
    @ApiResponse(responseCode = "200", description = "Listado de avistamientos",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AvistamientoResponseDTO.class))))
    @GetMapping
    public List<AvistamientoResponseDTO> listarTodos() {
        return avistamientoService.listarTodos();
    }

    @Operation(
            summary = "Listar avistamientos por mascota",
            description = "Devuelve todos los avistamientos de una mascota específica."
    )
    @ApiResponse(responseCode = "200", description = "Listado de avistamientos de la mascota",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AvistamientoResponseDTO.class))))
    @GetMapping("/mascota/{mascotaId}")
    public List<AvistamientoResponseDTO> listarPorMascota(@PathVariable Long mascotaId) {
        return avistamientoService.listarPorMascota(mascotaId);
    }

    @Operation(
            summary = "Listar avistamientos por usuario",
            description = "Devuelve todos los avistamientos realizados por un usuario específico."
    )
    @ApiResponse(responseCode = "200", description = "Listado de avistamientos del usuario",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AvistamientoResponseDTO.class))))
    @GetMapping("/usuario/{usuarioId}")
    public List<AvistamientoResponseDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return avistamientoService.listarPorUsuario(usuarioId);
    }

    @Operation(
            summary = "Listar avistamientos en posesión",
            description = "Devuelve todos los avistamientos donde el usuario tiene la mascota en posesión."
    )
    @ApiResponse(responseCode = "200", description = "Listado de avistamientos en posesión",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AvistamientoResponseDTO.class))))
    @GetMapping("/en-posesion")
    public List<AvistamientoResponseDTO> listarEnPosesion() {
        return avistamientoService.listarEnPosesion();
    }

    @Operation(
            summary = "Deshabilitar un avistamiento",
            description = "Marca un avistamiento como deshabilitado. No lo elimina físicamente de la base de datos."
    )
    @ApiResponse(responseCode = "204", description = "Avistamiento deshabilitado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        avistamientoService.deshabilitar(id);
    }
}

