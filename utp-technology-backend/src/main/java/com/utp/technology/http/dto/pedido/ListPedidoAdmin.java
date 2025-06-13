package com.utp.technology.http.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "cliente_id", "nombre_cliente", "dni", "usuario", "fecha", "estado", "total" })
public interface ListPedidoAdmin {

  Integer getId();

  @JsonProperty("cliente_id")
  Integer getClienteId();

  @JsonProperty("nombre_cliente")
  String getNombreCliente();

  String getDni();

  String getUsuario();

  String getFecha();

  String getEstado();

  Double getTotal();

}
