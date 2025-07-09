package com.example.usergym.services;

import com.example.usergym.models.Usuario;
import com.example.usergym.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> getAll() {
        return repo.findAll();
    }

    public Optional<Usuario> getById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public Usuario create(Usuario dto) {
        return repo.save(dto);
    }

    @Transactional
    public Usuario update(Integer id, Usuario cambios) {
        return repo.findById(id).map(u -> {
            u.setRol(cambios.getRol());
            u.setPersonaId(cambios.getPersonaId());
            u.setNombreUsuario(cambios.getNombreUsuario());
            u.setEmail(cambios.getEmail());
            u.setPassword(cambios.getPassword());
            return repo.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void delete(Integer id) {
        repo.findById(id).ifPresent(u -> {
            u.setEstado(false);
            repo.save(u);
        });
    }


    // UsuarioService.java
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return repo.findByNombreUsuario(nombreUsuario);
    }

}
