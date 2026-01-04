package com.tienda.ventas.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tienda.ventas.model.Usuario;
import com.tienda.ventas.repository.UsuarioRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            // Borra y crea de nuevo (DEV)
            repo.findByUsername("admin").ifPresent(repo::delete);
            repo.findByUsername("vendedor").ifPresent(repo::delete);

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            repo.save(admin);

            Usuario vendedor = new Usuario();
            vendedor.setUsername("vendedor");
            vendedor.setPassword(encoder.encode("vendedor123"));
            vendedor.setRoles(Set.of("ROLE_VENDEDOR"));
            repo.save(vendedor);

            System.out.println("✅ Usuarios creados: admin/admin123 y vendedor/vendedor123");
        };
    }
}
