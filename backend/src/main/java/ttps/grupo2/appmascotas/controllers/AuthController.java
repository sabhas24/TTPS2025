package ttps.grupo2.appmascotas.controllers;

import ttps.grupo2.appmascotas.DTOs.AuthDTOs.JwtResponseDTO;
import ttps.grupo2.appmascotas.DTOs.AuthDTOs.LoginRequestDTO;
import ttps.grupo2.appmascotas.entities.Usuario;
import ttps.grupo2.appmascotas.services.JWTService;
import ttps.grupo2.appmascotas.services.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final AuthenticationManager authManager;
        private final JWTService jwtService;
        private final UsuarioService usuarioService;

        @Autowired
        public AuthController(
                        AuthenticationManager authManager,
                        JWTService jwtService,
                        UsuarioService usuarioService) {

                this.authManager = authManager;
                this.jwtService = jwtService;
                this.usuarioService = usuarioService;
        }

        @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña, devolviendo un token JWT con datos del usuario.")
        @ApiResponse(responseCode = "200", description = "Login exitoso, token generado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class)))
        @ApiResponse(responseCode = "401", description = "Email o contraseña incorrectos")
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas o incompletas")
        @PostMapping("/login")
        public ResponseEntity<JwtResponseDTO> login(
                        @RequestBody @Valid LoginRequestDTO req) {
                try {
                        // 1️⃣ Autenticar credenciales
                        authManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        req.email(),
                                                        req.password()));

                        // 2️⃣ Obtener el Usuario real desde la BD
                        Usuario usuario = usuarioService.buscarPorEmail(req.email())
                                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                        // 3️⃣ Generar JWT con datos del usuario
                        String token = jwtService.generateToken(usuario);

                        // 4️⃣ Responder
                        return ResponseEntity.ok(new JwtResponseDTO(token));

                } catch (BadCredentialsException | UsernameNotFoundException ex) {
                        // Email o contraseña incorrectos -> 401
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        }
}
