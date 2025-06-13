package com.utp.technology.http.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.utp.technology.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({ "id", "id_rol", "correo", "nombre" })
public class UserLoginDto {

  private Integer id;

  @JsonProperty("id_rol")
  private Integer idRol;

  private String correo;

  private String nombre;

  public UserLoginDto(Usuario usuario) {
    this.id = usuario.getId();
    this.idRol = usuario.getRol().getIdRol();
    this.correo = usuario.getCorreo();
    this.nombre = usuario.getNombre();
  }

}
