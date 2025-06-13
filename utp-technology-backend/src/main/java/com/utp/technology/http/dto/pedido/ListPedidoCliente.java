package com.utp.technology.http.dto.pedido;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "fecha", "estado", "total" })
public interface ListPedidoCliente {

  Integer getId();

  String getFecha();

  String getEstado();

  Double getTotal();

}
