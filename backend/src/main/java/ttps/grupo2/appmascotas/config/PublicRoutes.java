package ttps.grupo2.appmascotas.config;

import java.util.List;

public class PublicRoutes {
    public static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login",
            "/usuarios/registrar",
            "/auth/**",
            "/error",
            "/mascotas/perdidas",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**");
}
