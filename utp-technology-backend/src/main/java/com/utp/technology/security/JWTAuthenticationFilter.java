package com.utp.technology.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final JWTService jwtService;

  private final CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException {

    // Controlador de autenticación
    if (request.getServletPath().contains("/api/v1/auth")) {
      chain.doFilter(request, response);
      return;
    }

    final String authorizationHeader = request.getHeader("Authorization");
    final String token;
    final String username;

    // Sin el header authorization o el token
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    token = authorizationHeader.substring(7);
    username = jwtService.extractUserName(token);

    // Validando
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (!jwtService.isTokenExpired(token)) {
        Claims claims = jwtService.extractAllClaims(token);
        Integer id = claims.get("id", Integer.class);
        Integer idRol = claims.get("rol", Integer.class);
        String correo = claims.get("correo", String.class);

        JwtUsuario jwtUsuario = new JwtUsuario();
        jwtUsuario.setId(id);
        jwtUsuario.setRolId(idRol);
        jwtUsuario.setCorreo(correo);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(jwtUsuario);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    chain.doFilter(request, response);
  }
}
