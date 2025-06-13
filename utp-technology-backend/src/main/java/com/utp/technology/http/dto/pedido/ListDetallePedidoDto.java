package com.utp.technology.http.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "pedido_id", "producto_id", "producto", "imagen_producto", "cantidad", "precio_unitario",
    "subtotal" })
public interface ListDetallePedidoDto {

  Integer getId();

  @JsonProperty("pedido_id")
  Integer getPedidoId();

  @JsonProperty("producto_id")
  Integer getProductoId();

  String getProducto();

  @JsonProperty("imagen_producto")
  String getImagenProducto();

  Integer getCantidad();

  @JsonProperty("precio_unitario")
  Double getPrecioUnitario();

  Double getSubtotal();

}
