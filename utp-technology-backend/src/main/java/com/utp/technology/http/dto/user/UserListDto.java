package com.utp.technology.http.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "id_rol", "correo", "nombre" })
public interface UserListDto {

  Long getId();

  @JsonProperty("id_rol")
  Integer getIdRol();

  String getCorreo();

  String getNombre();

}
