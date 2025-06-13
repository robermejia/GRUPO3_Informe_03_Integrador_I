package com.utp.technology.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utp.technology.http.dto.pedido.ListPedidoAdmin;
import com.utp.technology.http.dto.pedido.ListPedidoCliente;
import com.utp.technology.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

  @Query(value = """
      SELECT
        p.id,
        p.cliente_id,
        CONCAT(c.nombre, ' ', c.apellido) AS nombre_cliente,
        c.dni,
        u.nombre AS usuario,
        p.fecha,
        p.estado,
        SUM(dp.cantidad * dp.precio_unitario) AS total
      FROM pedidos AS p
      INNER JOIN clientes AS c
        ON c.id = p.cliente_id
      INNER JOIN usuarios AS u
        ON u.id = p.usuario_id
      LEFT JOIN detalles_pedido AS dp
        ON p.id = dp.pedido_id
      GROUP BY p.id, p.cliente_id, CONCAT(c.nombre, ' ', c.apellido), c.dni, u.nombre, p.fecha, p.estado
      """, nativeQuery = true)
  public Page<ListPedidoAdmin> listProductoAdmin(Pageable page);

  @Query(value = """
      SELECT
        p.id, p.fecha, p.estado, SUM(dp.cantidad * dp.precio_unitario) AS total
      FROM pedidos AS p
      INNER JOIN clientes AS c
        ON c.id = p.cliente_id
      LEFT JOIN detalles_pedido AS dp
        ON p.id = dp.pedido_id
      WHERE c.usuario_id = :usuarioId
      AND p.estado != 'Cancelado'
      GROUP BY p.id, p.cliente_id, p.fecha, p.estado
      """, nativeQuery = true)
  public Page<ListPedidoCliente> listProductoCustomer(@Param("usuarioId") Integer usuarioId, Pageable page);

  @Modifying
  @Query(value = """
        UPDATE detalles_pedido
        INNER JOIN productos AS p
          ON detalles_pedido.producto_id = p.id
        SET precio_unitario = p.precio
        WHERE detalles_pedido.pedido_id = :idPedido
      """, nativeQuery = true)
  public void actualizarPrecioUnitarioDetalles(@Param("idPedido") Integer idPedido);

}
