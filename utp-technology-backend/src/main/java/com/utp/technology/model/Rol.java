package com.utp.technology.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
}
