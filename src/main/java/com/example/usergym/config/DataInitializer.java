// src/main/java/com/example/usergym/config/DataInitializer.java
package com.example.usergym.config;

import com.example.usergym.models.Rol;
import com.example.usergym.models.Usuario;
import com.example.usergym.repositories.RolRepository;
import com.example.usergym.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepo,
                           UsuarioRepository usuarioRepo,
                           PasswordEncoder passwordEncoder) {
        this.rolRepo = rolRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Verifica o crea el rol ADMIN
        Rol adminRole = rolRepo.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Rol r = new Rol();
                    r.setNombre("ADMIN");
                    r.setEstado(true);
                    return rolRepo.save(r);
                });

        // 2. Verifica o crea el usuario "admin"
        usuarioRepo.findByNombreUsuario("admin")
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    u.setNombreUsuario("admin");
                    u.setPassword(passwordEncoder.encode("admin"));
                    u.setEmail("admin@example.com");
                    u.setRol(adminRole);
                    u.setPersonaId(0);       // dummy, ajustar según tu dominio
                    u.setEstado(true);
                    return usuarioRepo.save(u);
                });
    }
}
