package com.example.usergym.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    /** FK a Rol dentro de este mismo microservicio */
    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    /** referencia opaca a Persona en el microservicio de clientes */
    @Column(nullable = false)
    private Integer personaId;

    @Column(nullable = false, length = 50, unique = true)
    private String nombreUsuario;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean estado = true;
}
