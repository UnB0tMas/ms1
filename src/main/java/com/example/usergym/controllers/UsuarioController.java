package com.example.usergym.controllers;

import com.example.usergym.models.Usuario;
import com.example.usergym.services.UsuarioService;
import com.example.usergym.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<Usuario>> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario usuario = service.getByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(new ApiResponse<>(true, "Login correcto", usuario));
    }

    // Obtener lista de todos los usuarios
    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> list() {
        List<Usuario> usuarios = service.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de usuarios obtenida correctamente", usuarios));
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> get(@PathVariable Integer id) {
        return service.getById(id)
                .map(usuario -> ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", usuario)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Usuario no encontrado", null)));
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> create(@RequestBody Usuario dto) {
        Usuario nuevo = service.create(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario creado exitosamente", nuevo));
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> update(@PathVariable Integer id, @RequestBody Usuario cambios) {
        try {
            Usuario actualizado = service.update(id, cambios);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado correctamente", actualizado));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    // Eliminar (inhabilitar) un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado correctamente", null));
    }
}
