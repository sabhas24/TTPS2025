package ttps.grupo2.appmascotas.DTOs.AuthDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8) String password) {
}
