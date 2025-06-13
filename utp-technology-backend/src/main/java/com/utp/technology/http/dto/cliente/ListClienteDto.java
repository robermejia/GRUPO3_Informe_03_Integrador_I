package com.utp.technology.http.dto.cliente;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "nombre", "apellido", "dni", "telefono", "correo" })
public interface ListClienteDto {

  Integer getId();

  String getNombre();

  String getApellido();

  String getDni();

  String getTelefono();

  String getCorreo();

}
