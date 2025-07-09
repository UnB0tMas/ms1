package com.example.usergym.services;

import com.example.usergym.models.Rol;
import com.example.usergym.repositories.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository repo;

    public RolService(RolRepository repo) {
        this.repo = repo;
    }

    public List<Rol> getAll() {
        return repo.findAll();
    }

    public Optional<Rol> getById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public Rol create(Rol dto) {
        return repo.save(dto);
    }

    @Transactional
    public Rol update(Integer id, Rol cambios) {
        return repo.findById(id).map(r -> {
            r.setNombre(cambios.getNombre());
            return repo.save(r);
        }).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @Transactional
    public void delete(Integer id) {
        repo.findById(id).ifPresent(r -> {
            r.setEstado(false);
            repo.save(r);
        });
    }
}
