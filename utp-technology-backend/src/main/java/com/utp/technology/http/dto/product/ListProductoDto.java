package com.utp.technology.http.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "nombre", "imagen", "precio", "stock" })
public interface ListProductoDto {

  @JsonProperty("id")
  Integer getId();

  @JsonProperty("nombre")
  String getNombre();

  @JsonProperty("imagen")
  String getImagen();

  @JsonProperty("precio")
  Double getPrecio();

  @JsonProperty("stock")
  Integer getStock();

}
