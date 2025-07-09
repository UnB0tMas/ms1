package com.example.usergym.controllers;

import com.example.usergym.models.Rol;
import com.example.usergym.services.RolService;
import com.example.usergym.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Rol>>> list() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lista de roles obtenida correctamente", service.getAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> get(@PathVariable Integer id) {
        return service.getById(id)
                .map(rol -> ResponseEntity.ok(new ApiResponse<>(true, "Rol encontrado", rol)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Rol no encontrado", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Rol>> create(@RequestBody Rol dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Rol creado exitosamente", service.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Rol>> update(@PathVariable Integer id, @RequestBody Rol cambios) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol actualizado correctamente", service.update(id, cambios)));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rol eliminado correctamente", null));
    }
}
