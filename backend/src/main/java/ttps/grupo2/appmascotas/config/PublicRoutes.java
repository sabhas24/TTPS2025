package ttps.grupo2.appmascotas.config;

import java.util.List;

public class PublicRoutes {
    public static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login",
            "/usuarios/registrar",
            "/error",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html");
}
