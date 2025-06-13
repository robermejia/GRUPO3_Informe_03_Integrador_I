package com.utp.technology.http.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

  @Email(message = "Debe ser un formato de correo electrónico válido")
  @NotBlank(message = "El correo es obligatorio")
  private String email;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

}
