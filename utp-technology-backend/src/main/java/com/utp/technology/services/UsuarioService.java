package com.utp.technology.services;

import java.util.List;
import java.util.Optional;

import com.utp.technology.http.dto.user.UserListDto;
import com.utp.technology.model.Usuario;

public interface UsuarioService {

  public List<UserListDto> listAll();

  public Optional<Usuario> findById(Integer id);

  public Optional<Usuario> findByCorreo(String correo);

  public Usuario registrarUsuario(Usuario usuario);

  public boolean checkPassword(String hashedPassword, String password);

}
