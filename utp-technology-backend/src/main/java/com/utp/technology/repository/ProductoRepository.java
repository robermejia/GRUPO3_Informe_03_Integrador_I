package com.utp.technology.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utp.technology.http.dto.product.ListProductoDto;
import com.utp.technology.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

  @Query(value = """
        SELECT
          id, nombre, imagen, precio, stock
        FROM productos
        WHERE 1 = 1
        AND (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%'))
      """, countQuery = """
        SELECT COUNT(1) FROM productos
        WHERE 1 = 1
        AND (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%'))
      """, nativeQuery = true)
  public Page<ListProductoDto> listProducto(@Param("nombre") String nombre, Pageable page);

}
