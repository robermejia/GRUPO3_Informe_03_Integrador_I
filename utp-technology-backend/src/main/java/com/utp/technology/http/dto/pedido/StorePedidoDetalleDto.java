package com.utp.technology.http.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StorePedidoDetalleDto {

  @NotNull
  @Positive
  @JsonProperty("id_producto")
  private Integer idProducto;

  @NotNull
  @Positive
  private Integer cantidad;

}
