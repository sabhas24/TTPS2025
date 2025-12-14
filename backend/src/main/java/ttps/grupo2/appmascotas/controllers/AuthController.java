package ttps.grupo2.appmascotas.controllers;

import ttps.grupo2.appmascotas.DTOs.AuthDTOs.JwtResponseDTO;
import ttps.grupo2.appmascotas.DTOs.AuthDTOs.LoginRequestDTO;
import ttps.grupo2.appmascotas.services.JWTService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;

    public AuthController(AuthenticationManager authManager, UserDetailsService uds, JWTService jwtService) {
        this.authManager = authManager;
        this.userDetailsService = uds;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña, devolviendo un token JWT válido por 1 hora.")
    @ApiResponse(responseCode = "200", description = "Login exitoso, token generado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Email o contraseña incorrectos")
    @ApiResponse(responseCode = "400", description = "Credenciales inválidas o incompletas")
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO req) {
        // 1. Autenticar credenciales
        var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        authManager.authenticate(authToken);

        // 2. Generar token JWT
        var userDetails = userDetailsService.loadUserByUsername(req.email());
        var token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }
}
