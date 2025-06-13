package com.utp.technology.http.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.technology.http.dto.auth.LoginRequestDTO;
import com.utp.technology.http.dto.auth.LoginResponseDto;
import com.utp.technology.http.dto.auth.SignInRequestDTO;
import com.utp.technology.http.dto.user.UserLoginDto;
import com.utp.technology.http.response.ApiResponse;
import com.utp.technology.model.Rol;
import com.utp.technology.model.Usuario;
import com.utp.technology.security.JWTService;
import com.utp.technology.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final JWTService jwtService;

  private final UsuarioService usuarioService;

  @PostMapping("/sign-in")
  public ResponseEntity<ApiResponse<Usuario>> signIn(@Valid @RequestBody SignInRequestDTO request,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errors.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(null,
          errors.toString()));
    }

    Optional<Usuario> usuario = this.usuarioService.findByCorreo(request.getEmail());

    if (usuario.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.notFound("Correo ya registrado"));
    }

    Usuario newUser = new Usuario();
    newUser.setNombre(request.getNombre());
    newUser.setCorreo(request.getEmail());
    newUser.setClave(request.getPassword());
    Rol rol = new Rol();
    rol.setIdRol(2);
    newUser.setRol(rol);

    newUser = this.usuarioService.registrarUsuario(newUser);

    return ResponseEntity.ok().body(ApiResponse.success(newUser));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDTO request,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errors.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.badRequest(null,
          errors.toString()));
    }

    Optional<Usuario> usuario = this.usuarioService.findByCorreo(request.getEmail());

    if (usuario.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.notFound("Credenciales incorrectas"));
    }

    if (!this.usuarioService.checkPassword(usuario.get().getClave(), request.getPassword())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.notFound("Credenciales incorrectas"));
    }

    String token = this.jwtService.generateToken(usuario.get());

    LoginResponseDto res = new LoginResponseDto();
    res.setAccessToken(token);
    res.setUser(new UserLoginDto(usuario.get()));
    return ResponseEntity.ok().body(ApiResponse.success(res, "Login Exitoso"));
  }

}
