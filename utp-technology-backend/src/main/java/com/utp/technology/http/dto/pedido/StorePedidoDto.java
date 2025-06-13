package com.utp.technology.http.dto.pedido;

import java.util.List;

import lombok.Data;

@Data
public class StorePedidoDto {
  private List<StorePedidoDetalleDto> detalles;
}
