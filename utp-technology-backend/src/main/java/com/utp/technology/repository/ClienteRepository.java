package com.utp.technology.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utp.technology.http.dto.cliente.ListClienteDto;
import com.utp.technology.model.Cliente;
import com.utp.technology.model.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

  public Optional<Cliente> findByUsuario(Usuario usuario);

  @Query(value = """
        SELECT
          id, nombre, apellido, dni, telefono, correo
        FROM clientes AS c
        WHERE 1 = 1
        AND (:busqueda IS NULL OR (
          nombre LIKE CONCAT('%', :busqueda, '%') OR
          apellido LIKE CONCAT('%', :busqueda, '%') OR
          dni LIKE CONCAT('%', :busqueda, '%') OR
          telefono LIKE CONCAT('%', :busqueda, '%') OR
          correo LIKE CONCAT('%', :busqueda, '%')
        ))
      """, countQuery = """
        SELECT
          COUNT(1)
        FROM clientes AS c
        WHERE 1 = 1
        AND (:busqueda IS NULL OR (
          nombre LIKE CONCAT('%', :busqueda, '%') OR
          apellido LIKE CONCAT('%', :busqueda, '%') OR
          dni LIKE CONCAT('%', :busqueda, '%') OR
          telefono LIKE CONCAT('%', :busqueda, '%') OR
          correo LIKE CONCAT('%', :busqueda, '%')
        ))
      """, nativeQuery = true)
  public Page<ListClienteDto> listCliente(@Param("busqueda") String busqueda, Pageable page);
}
