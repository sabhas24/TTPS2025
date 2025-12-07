package ttps.grupo2.appmascotas.controllers;

import ttps.grupo2.appmascotas.DTOs.AuthDTOs.JwtResponseDTO;
import ttps.grupo2.appmascotas.DTOs.AuthDTOs.LoginRequestDTO;
import ttps.grupo2.appmascotas.services.JWTService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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
