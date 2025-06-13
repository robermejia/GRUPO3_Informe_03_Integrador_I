package com.utp.technology.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utp.technology.http.dto.user.UserListDto;
import com.utp.technology.model.Rol;
import com.utp.technology.model.Usuario;
import com.utp.technology.repository.RolRepository;
import com.utp.technology.repository.UsuarioRepository;
import com.utp.technology.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final RolRepository rolRepository;

  @Override
  public List<UserListDto> listAll() {
    return this.usuarioRepository.listAll();
  }

  @Override
  public Optional<Usuario> findById(Integer id) {
    return this.usuarioRepository.findById(id);
  }

  @Override
  public Optional<Usuario> findByCorreo(String correo) {
    return this.usuarioRepository.findByCorreo(correo);
  }

  @Override
  public Usuario registrarUsuario(Usuario usuario) {
    String hashedPassword = this.passwordEncoder.encode(usuario.getClave());
    usuario.setClave(hashedPassword);

    if (usuario.getRol() == null) {
      Rol rolUser = rolRepository.findByNombreRol("USER")
          .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
      usuario.setRol(rolUser);
    }

    return this.usuarioRepository.save(usuario);
  }

  @Override
  public boolean checkPassword(String hashedPassword, String password) {
    return this.passwordEncoder.matches(password, hashedPassword);
  }

}
