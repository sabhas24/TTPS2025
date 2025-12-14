package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.DTOs.Avistamientos.*;

import ttps.grupo2.appmascotas.services.AvistamientoService;

@RestController
@RequestMapping("/avistamientos")
public class AvistamientoController {

    @Autowired
    private AvistamientoService avistamientoService;

    @Operation(summary = "Crear un nuevo avistamiento", description = "Registra un avistamiento de una mascota reportado por un usuario.")
    @ApiResponse(responseCode = "201", description = "Avistamiento creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvistamientoResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Mascota o usuario no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvistamientoResponseDTO crear(@RequestBody AvistamientoCreateRequestDTO dto) {
        return avistamientoService.crearAvistamiento(dto);
    }

    @Operation(summary = "Listar todos los avistamientos", description = "Devuelve una lista de todos los avistamientos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de avistamientos")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @GetMapping
    public java.util.List<AvistamientoResponseDTO> listar() {
        return avistamientoService.listarTodos();
    }

    @Operation(summary = "Buscar avistamiento por ID", description = "Devuelve los detalles de un avistamiento específico.")
    @ApiResponse(responseCode = "200", description = "Avistamiento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvistamientoResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Avistamiento no encontrado")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @GetMapping("/{id}")
    public AvistamientoResponseDTO buscarPorId(@PathVariable Long id) {
        return avistamientoService.buscarPorId(id);
    }

    @Operation(summary = "Actualizar un avistamiento", description = "Modifica los datos de un avistamiento existente.")
    @ApiResponse(responseCode = "200", description = "Avistamiento actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvistamientoResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Avistamiento no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @PutMapping("/{id}")
    public AvistamientoResponseDTO actualizar(@PathVariable Long id, @RequestBody AvistamientoUpdateRequestDTO dto) {
        return avistamientoService.actualizarAvistamiento(id, dto);
    }

    @Operation(summary = "Deshabilitar avistamiento", description = "Marca un avistamiento como deshabilitado.")
    @ApiResponse(responseCode = "204", description = "Avistamiento deshabilitado correctamente")
    @ApiResponse(responseCode = "404", description = "Avistamiento no encontrado")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        avistamientoService.deshabilitar(id);
    }

    @Operation(summary = "Listar avistamientos por mascota", description = "Devuelve todos los avistamientos asociados a una mascota específica.")
    @ApiResponse(responseCode = "200", description = "Lista de avistamientos de la mascota")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @GetMapping("/mascota/{mascotaId}")
    public java.util.List<AvistamientoResponseDTO> listarPorMascota(@PathVariable Long mascotaId) {
        return avistamientoService.getAvistamientosPorMascota(mascotaId);
    }
}
