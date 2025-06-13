package com.utp.technology.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utp.technology.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(String nombreRol);
}
