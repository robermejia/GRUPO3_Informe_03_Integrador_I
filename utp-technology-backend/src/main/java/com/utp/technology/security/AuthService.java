package com.utp.technology.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  public JwtUsuario getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    JwtUsuario details = (JwtUsuario) authentication.getDetails();
    return details;
  }

}
