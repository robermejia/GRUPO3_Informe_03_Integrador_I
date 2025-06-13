package com.utp.technology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utp.technology.http.dto.pedido.ListDetallePedidoDto;
import com.utp.technology.model.DetallePedido;

public interface PedidoDetalleRepository extends JpaRepository<DetallePedido, Integer> {

  @Query(value = """
        SELECT
          dp.id,
          dp.pedido_id,
          dp.producto_id,
          p.nombre AS producto,
          p.imagen AS imagen_producto,
          dp.cantidad,
          dp.precio_unitario,
          dp.cantidad * dp.precio_unitario AS subtotal
        FROM detalles_pedido AS dp
        INNER JOIN productos AS p
          ON p.id = dp.producto_id
        WHERE dp.pedido_id = :idPedido
        ORDER BY dp.id
      """, nativeQuery = true)
  public List<ListDetallePedidoDto> listPedidoDetalles(@Param("idPedido") Integer idPedido);

}
