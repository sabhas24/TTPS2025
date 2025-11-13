package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaCreateRequestDTO;
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaResponseDTO;
import ttps.grupo2.appmascotas.DTOs.MascotasDTOs.MascotaUpdateRequestDTO;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.services.MascotaService;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Operation(
            summary = "Publicar una nueva mascota",
            description = "Registra una nueva mascota en estado PERDIDO_PROPIO. Requiere el ID del usuario publicador."
    )
    @ApiResponse(responseCode = "201", description = "Mascota publicada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MascotaResponseDTO publicar(@RequestBody MascotaCreateRequestDTO dto) {
        return mascotaService.publicarMascota(dto);
    }

    @Operation(
            summary = "Editar una mascota existente",
            description = "Actualiza los datos de una mascota habilitada. No permite modificar estado ni publicador."
    )
    @ApiResponse(responseCode = "200", description = "Mascota actualizada correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PutMapping("/{id}")
    public MascotaResponseDTO editar(@PathVariable Long id, @RequestBody MascotaUpdateRequestDTO dto) {
        return mascotaService.editarMascota(id, dto);
    }

    @Operation(
            summary = "Cambiar el estado de una mascota",
            description = "Modifica el estado de una mascota según las transiciones definidas (ej. PERDIDO → RECUPERADO)."
    )
    @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PatchMapping("/{id}/estado")
    public MascotaResponseDTO cambiarEstado(@PathVariable Long id, @RequestParam EstadoMascota estado) {
        return mascotaService.cambiarEstado(id, estado);
    }

    @Operation(
            summary = "Deshabilitar una mascota",
            description = "Marca una mascota como deshabilitada. No la elimina físicamente de la base de datos."
    )
    @ApiResponse(responseCode = "204", description = "Mascota deshabilitada correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        mascotaService.deshabilitarMascota(id);
    }

    @Operation(
            summary = "Listar mascotas perdidas",
            description = "Devuelve todas las mascotas en estado PERDIDO_PROPIO que estén habilitadas."
    )
    @ApiResponse(responseCode = "200", description = "Listado de mascotas perdidas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MascotaResponseDTO.class))))
    @GetMapping("/perdidas")
    public List<MascotaResponseDTO> listarPerdidas() {
        return mascotaService.listarMascotasPerdidas();
    }

    @Operation(
            summary = "Listar mascotas por usuario",
            description = "Devuelve todas las mascotas habilitadas publicadas por el usuario indicado."
    )
    @ApiResponse(responseCode = "200", description = "Listado de mascotas del usuario",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MascotaResponseDTO.class))))
    @GetMapping("/usuario/{id}")
    public List<MascotaResponseDTO> listarPorUsuario(@PathVariable Long id) {
        return mascotaService.listarPorUsuario(id);
    }
}