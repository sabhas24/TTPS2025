package ttps.grupo2.appmascotas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ttps.grupo2.appmascotas.dto.MascotaRequestDTO;
import ttps.grupo2.appmascotas.dto.MascotaResponseDTO;
import ttps.grupo2.appmascotas.entities.EstadoMascota;
import ttps.grupo2.appmascotas.services.MascotaService;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Operation(summary = "Publicar nueva mascota", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponse(responseCode = "201", description = "Mascota publicada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MascotaResponseDTO publicar(@RequestBody MascotaRequestDTO dto) {
        return mascotaService.publicarMascota(dto);
    }

    @Operation(summary = "Editar mascota existente", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponse(responseCode = "200", description = "Mascota actualizada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PutMapping("/{id}")
    public MascotaResponseDTO editar(@PathVariable Long id, @RequestBody MascotaRequestDTO dto) {
        return mascotaService.editarMascota(id, dto);
    }

    @Operation(summary = "Cambiar estado de mascota", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponse(responseCode = "200", description = "Estado actualizado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponseDTO.class)))
    @PatchMapping("/{id}/estado")
    public MascotaResponseDTO cambiarEstado(@PathVariable Long id, @RequestParam EstadoMascota estado) {
        return mascotaService.cambiarEstado(id, estado);
    }

    @Operation(summary = "Deshabilitar mascota", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponse(responseCode = "204", description = "Mascota deshabilitada")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deshabilitar(@PathVariable Long id) {
        mascotaService.deshabilitarMascota(id);
    }

    @Operation(summary = "Listar mascotas perdidas")
    @ApiResponse(responseCode = "200", description = "Mascotas perdidas encontradas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MascotaResponseDTO.class))))
    @GetMapping("/perdidas")
    public List<MascotaResponseDTO> listarPerdidas() {
        return mascotaService.listarMascotasPerdidas();
    }

    @Operation(summary = "Listar mascotas habilitadas por usuario")
    @ApiResponse(responseCode = "200", description = "Mascotas encontradas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MascotaResponseDTO.class))))
    @GetMapping("/usuario/{id}")
    public List<MascotaResponseDTO> listarPorUsuario(@PathVariable Long id) {
        return mascotaService.listarPorUsuario(id);
    }
}