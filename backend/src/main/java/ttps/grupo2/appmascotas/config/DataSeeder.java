package ttps.grupo2.appmascotas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ttps.grupo2.appmascotas.entities.*;
import ttps.grupo2.appmascotas.entities.TipoUsuario;
import ttps.grupo2.appmascotas.repositories.AvistamientoRepository;
import ttps.grupo2.appmascotas.repositories.MascotaRepository;
import ttps.grupo2.appmascotas.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("dev") // Solo se ejecuta con el profile "dev"
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            MascotaRepository mascotaRepository,
            AvistamientoRepository avistamientoRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Solo inserta datos si la base está vacía
            if (usuarioRepository.count() > 0) {
                System.out.println("⚠️  Base de datos ya tiene datos. Saltando seed.");
                return;
            }

            System.out.println("🌱 Insertando datos de ejemplo...");

            // Crear usuarios de ejemplo
            Usuario usuario1 = new Usuario();
            usuario1.setNombre("Juan");
            usuario1.setApellido("Pérez");
            usuario1.setEmail("juan@example.com");
            usuario1.setContraseña(passwordEncoder.encode("password123"));
            usuario1.setTelefono("1123456789");
            usuario1.setBarrio("Palermo");
            usuario1.setCiudad("Buenos Aires");
            usuario1.setTipo(TipoUsuario.USUARIO);
            usuario1.setHabilitado(true);
            usuarioRepository.save(usuario1);

            Usuario usuario2 = new Usuario();
            usuario2.setNombre("Pablo");
            usuario2.setApellido("redruello");
            usuario2.setEmail("pabliito.23.94@gmail.com");
            usuario2.setContraseña(passwordEncoder.encode("     "));
            usuario2.setTelefono("1198765432");
            usuario2.setBarrio("la plata");
            usuario2.setCiudad("la plata");
            usuario2.setTipo(TipoUsuario.USUARIO);
            usuario2.setHabilitado(true);
            usuarioRepository.save(usuario2);

            Usuario usuario3 = new Usuario();
            usuario3.setNombre("Carlos");
            usuario3.setApellido("López");
            usuario3.setEmail("carlos@example.com");
            usuario3.setContraseña(passwordEncoder.encode("password123"));
            usuario3.setTelefono("1187654321");
            usuario3.setBarrio("Belgrano");
            usuario3.setCiudad("Buenos Aires");
            usuario3.setTipo(TipoUsuario.USUARIO);
            usuario3.setHabilitado(true);
            usuarioRepository.save(usuario3);

            // Crear mascotas perdidas de ejemplo
            Mascota mascota1 = new Mascota();
            mascota1.setNombre("Max");
            mascota1.setTamanio(2);
            mascota1.setColor("Marrón");
            mascota1.setDescripcionExtra("Perro Golden Retriever, muy amigable. Tiene collar azul.");
            mascota1.setFotos(List.of("https://images.dog.ceo/breeds/retriever-golden/n02099601_100.jpg"));
            mascota1.setCoordenada(new Coordenada(-34.5875, -58.4080, "Palermo"));
            mascota1.setPublicador(usuario1);
            mascota1.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota1.setHabilitado(true);
            mascotaRepository.save(mascota1);

            Mascota mascota2 = new Mascota();
            mascota2.setNombre("Luna");
            mascota2.setTamanio(1);
            mascota2.setColor("Blanco y negro");
            mascota2.setDescripcionExtra("Gata siamesa, ojos azules. Muy tímida.");
            mascota2.setFotos(List.of("https://images.dog.ceo/breeds/siamese/n02124075_1783.jpg"));
            mascota2.setCoordenada(new Coordenada(-34.5889, -58.3969, "Recoleta"));
            mascota2.setPublicador(usuario2);
            mascota2.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota2.setHabilitado(true);
            mascotaRepository.save(mascota2);

            Mascota mascota3 = new Mascota();
            mascota3.setNombre("Rocky");
            mascota3.setTamanio(3);
            mascota3.setColor("Negro");
            mascota3.setDescripcionExtra("Labrador negro adulto. Muy juguetón y le encanta nadar.");
            mascota3.setFotos(List.of("https://images.dog.ceo/breeds/labrador/n02099712_1181.jpg"));
            mascota3.setCoordenada(new Coordenada(-34.5627, -58.4556, "Belgrano"));
            mascota3.setPublicador(usuario3);
            mascota3.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota3.setHabilitado(true);
            mascotaRepository.save(mascota3);

            Mascota mascota4 = new Mascota();
            mascota4.setNombre("Milo");
            mascota4.setTamanio(1);
            mascota4.setColor("Atigrado");
            mascota4.setDescripcionExtra("Gato atigrado con manchas blancas. Muy cariñoso.");
            mascota4.setFotos(List.of("https://images.dog.ceo/breeds/shiba/shiba-11.jpg"));
            mascota4.setCoordenada(new Coordenada(-34.5702, -58.4214, "Palermo Soho"));
            mascota4.setPublicador(usuario1);
            mascota4.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota4.setHabilitado(true);
            mascotaRepository.save(mascota4);

            Mascota mascota5 = new Mascota();
            mascota5.setNombre("Bella");
            mascota5.setTamanio(2);
            mascota5.setColor("Dorado");
            mascota5.setDescripcionExtra("Cocker Spaniel, pelaje largo y dorado. Tiene chip.");
            mascota5.setFotos(List.of("https://images.dog.ceo/breeds/cocker-spaniel/n02102318_3410.jpg"));
            mascota5.setCoordenada(new Coordenada(-34.5812, -58.4093, "Barrio Norte"));
            mascota5.setPublicador(usuario2);
            mascota5.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota5.setHabilitado(true);
            mascotaRepository.save(mascota5);

            // Mascotas en otros estados para probar funcionalidades
            Mascota mascota6 = new Mascota();
            mascota6.setNombre("Toby");
            mascota6.setTamanio(2);
            mascota6.setColor("Blanco y marrón");
            mascota6.setDescripcionExtra("Beagle muy activo. Castrado y vacunado.");
            mascota6.setFotos(List.of("https://images.dog.ceo/breeds/beagle/n02088364_11136.jpg"));
            mascota6.setCoordenada(new Coordenada(-34.5888, -58.3974, "Recoleta"));
            mascota6.setPublicador(usuario3);
            mascota6.setEstado(EstadoMascota.EN_ADOPCION);
            mascota6.setHabilitado(true);
            mascotaRepository.save(mascota6);

            Mascota mascota7 = new Mascota();
            mascota7.setNombre("Pelusa");
            mascota7.setTamanio(1);
            mascota7.setColor("Gris");
            mascota7.setDescripcionExtra("Gata persa, pelo largo. Muy tranquila.");
            mascota7.setFotos(List.of("https://images.dog.ceo/breeds/poodle-toy/n02113624_2294.jpg"));
            mascota7.setCoordenada(new Coordenada(-34.5703, -58.4215, "Palermo"));
            mascota7.setPublicador(usuario1);
            mascota7.setEstado(EstadoMascota.EN_ADOPCION);
            mascota7.setHabilitado(true);
            mascotaRepository.save(mascota7);

            Mascota mascota8 = new Mascota();
            mascota8.setNombre("Thor");
            mascota8.setTamanio(3);
            mascota8.setColor("Negro y dorado");
            mascota8.setDescripcionExtra("Pastor Alemán. Fue encontrado y recuperado.");
            mascota8.setFotos(List.of("https://images.dog.ceo/breeds/germanshepherd/n02106662_1074.jpg"));
            mascota8.setCoordenada(new Coordenada(-34.5628, -58.4557, "Belgrano"));
            mascota8.setPublicador(usuario2);
            mascota8.setEstado(EstadoMascota.RECUPERADO);
            mascota8.setHabilitado(true);
            mascotaRepository.save(mascota8);

            Mascota mascota9 = new Mascota();
            mascota9.setNombre("Nala");
            mascota9.setTamanio(2);
            mascota9.setColor("Caramelo");
            mascota9.setDescripcionExtra("Perra mestiza vista en el parque. No tiene dueño conocido.");
            mascota9.setFotos(List.of("https://images.dog.ceo/breeds/ridgeback-rhodesian/n02087394_4041.jpg"));
            mascota9.setCoordenada(new Coordenada(-34.5813, -58.4094, "Barrio Norte"));
            mascota9.setPublicador(usuario3);
            mascota9.setEstado(EstadoMascota.PERDIDO_AJENO);
            mascota9.setHabilitado(true);
            mascotaRepository.save(mascota9);

            Mascota mascota10 = new Mascota();
            mascota10.setNombre("Simba");
            mascota10.setTamanio(1);
            mascota10.setColor("Naranja");
            mascota10.setDescripcionExtra("Gato naranja. Fue adoptado recientemente.");
            mascota10.setFotos(List.of("https://images.dog.ceo/breeds/shiba/shiba-8.jpg"));
            mascota10.setCoordenada(new Coordenada(-34.5876, -58.4081, "Palermo"));
            mascota10.setPublicador(usuario1);
            mascota10.setEstado(EstadoMascota.ADOPTADO);
            mascota10.setHabilitado(true);
            mascotaRepository.save(mascota10);

            Mascota mascota11 = new Mascota();
            mascota11.setNombre("Rex");
            mascota11.setTamanio(3);
            mascota11.setColor("Blanco");
            mascota11.setDescripcionExtra("Dálmata. Está en posesión del dueño.");
            mascota11.setFotos(List.of("https://images.dog.ceo/breeds/dalmatian/cooper2.jpg"));
            mascota11.setCoordenada(new Coordenada(-34.5889, -58.3970, "Recoleta"));
            mascota11.setPublicador(usuario2);
            mascota11.setEstado(EstadoMascota.EN_POSESION);
            mascota11.setHabilitado(true);
            mascotaRepository.save(mascota11);

            // Mascota deshabilitada para probar filtros
            Mascota mascota12 = new Mascota();
            mascota12.setNombre("Odin");
            mascota12.setTamanio(2);
            mascota12.setColor("Gris");
            mascota12.setDescripcionExtra("Publicación antigua, ya no válida.");
            mascota12.setFotos(List.of("https://images.dog.ceo/breeds/husky/n02110185_1469.jpg"));
            mascota12.setCoordenada(new Coordenada(-34.5702, -58.4214, "Palermo"));
            mascota12.setPublicador(usuario3);
            mascota12.setEstado(EstadoMascota.PERDIDO_PROPIO);
            mascota12.setHabilitado(false); // Deshabilitada
            mascotaRepository.save(mascota12);

            // Avistamientos de mascotas perdidas
            Avistamiento avistamiento1 = new Avistamiento();
            avistamiento1.setFecha(LocalDateTime.now().minusDays(2));
            avistamiento1.setCoordenada(new Coordenada(-34.5880, -58.4085, "Palermo"));
            avistamiento1.setComentario("Lo vi cerca de la plaza. Estaba con collar azul.");
            avistamiento1.setFotos(List.of("https://images.dog.ceo/breeds/retriever-golden/n02099601_176.jpg"));
            avistamiento1.setEnPosesion(false);
            avistamiento1.setMascota(mascota1);
            avistamiento1.setUsuario(usuario2);
            avistamiento1.setHabilitado(true);
            avistamientoRepository.save(avistamiento1);

            Avistamiento avistamiento2 = new Avistamiento();
            avistamiento2.setFecha(LocalDateTime.now().minusDays(1));
            avistamiento2.setCoordenada(new Coordenada(-34.5890, -58.3975, "Recoleta"));
            avistamiento2.setComentario("Gata blanca y negra, muy asustada. Corrió hacia los árboles.");
            avistamiento2.setFotos(List.of("https://images.dog.ceo/breeds/siamese/n02124075_2089.jpg"));
            avistamiento2.setEnPosesion(false);
            avistamiento2.setMascota(mascota2);
            avistamiento2.setUsuario(usuario3);
            avistamiento2.setHabilitado(true);
            avistamientoRepository.save(avistamiento2);

            Avistamiento avistamiento3 = new Avistamiento();
            avistamiento3.setFecha(LocalDateTime.now().minusHours(5));
            avistamiento3.setCoordenada(new Coordenada(-34.5630, -58.4560, "Belgrano"));
            avistamiento3.setComentario("Lo tengo en mi casa. Encontré al labrador negro jugando en el parque.");
            avistamiento3.setFotos(List.of("https://images.dog.ceo/breeds/labrador/n02099712_2816.jpg"));
            avistamiento3.setEnPosesion(true);
            avistamiento3.setMascota(mascota3);
            avistamiento3.setUsuario(usuario1);
            avistamiento3.setHabilitado(true);
            avistamientoRepository.save(avistamiento3);

            Avistamiento avistamiento4 = new Avistamiento();
            avistamiento4.setFecha(LocalDateTime.now().minusDays(3));
            avistamiento4.setCoordenada(new Coordenada(-34.5705, -58.4220, "Palermo Soho"));
            avistamiento4.setComentario("Gato atigrado visto en la terraza de un edificio.");
            avistamiento4.setFotos(List.of());
            avistamiento4.setEnPosesion(false);
            avistamiento4.setMascota(mascota4); // Milo
            avistamiento4.setUsuario(usuario2);
            avistamiento4.setHabilitado(true);
            avistamientoRepository.save(avistamiento4);

            for (int i = 1; i <= 8; i++) {
                Mascota mascotaExtra = new Mascota();
                mascotaExtra.setNombre("Mascota " + i);
                mascotaExtra.setTamanio(i % 3 + 1);
                mascotaExtra.setColor("Color " + i);
                mascotaExtra.setDescripcionExtra("Mascota de prueba número " + i);
                mascotaExtra.setFotos(List.of("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"));
                mascotaExtra.setCoordenada(new Coordenada(-34.58 + (i * 0.01), -58.40 + (i * 0.01), "Buenos Aires"));
                mascotaExtra.setPublicador(usuario1);
                mascotaExtra.setEstado(i % 2 == 0 ? EstadoMascota.EN_POSESION : EstadoMascota.PERDIDO_PROPIO);
                mascotaExtra.setHabilitado(true);
                mascotaRepository.save(mascotaExtra);
            }

            System.out.println("✅ Datos de ejemplo insertados:");
            System.out.println("   - 3 usuarios:");
            System.out.println("     * juan@example.com / password123");
            System.out.println("     * pabliito.23.94@gmail.com / password123");
            System.out.println("     * carlos@example.com / password123");
            System.out.println("   - 20 mascotas en diferentes estados:");
            System.out.println("     * 5 PERDIDO_PROPIO (habilitadas)");
            System.out.println("     * 2 EN_ADOPCION");
            System.out.println("     * 1 RECUPERADO");
            System.out.println("     * 1 PERDIDO_AJENO");
            System.out.println("     * 1 ADOPTADO");
            System.out.println("     * 1 EN_POSESION");
            System.out.println("     * 1 PERDIDO_PROPIO (deshabilitada)");
            System.out.println("     * 8 mascotas extras para paginación");
            System.out.println("   - 4 avistamientos (1 con mascota en posesión)");
        };
    }
}
