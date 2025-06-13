package com.utp.technology.http.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.technology.http.dto.user.UserListDto;
import com.utp.technology.http.response.ApiResponse;
import com.utp.technology.model.Usuario;
import com.utp.technology.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<UserListDto>>> index() {
    return ResponseEntity.ok().body(ApiResponse.success(this.usuarioService.listAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Usuario>> show(@PathVariable() Integer id) {
    Optional<Usuario> usuario = this.usuarioService.findById(id);

    if (usuario.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notFound());
    }

    return ResponseEntity.ok().body(ApiResponse.success(usuario.get()));
  }

}
