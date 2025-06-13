package com.utp.technology.http.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequestDTO {

  @Email(message = "Debe ser un formato de correo electrónico válido")
  @NotBlank(message = "El correo es obligatorio")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

  @NotBlank(message = "El nombre es obligatorio")
  private String nombre;

}
