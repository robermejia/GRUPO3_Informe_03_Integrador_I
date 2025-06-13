package com.utp.technology.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utp.technology.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

  @Value("${jwt.secret}")
  private String secretKey;

  private static final long expirationTime = 1000 * 60 * 60 * 8; // 8 horas

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Generar un JWT para un usuario
   */
  public String generateToken(Usuario usuario) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", usuario.getId());
    claims.put("rol", usuario.getRol().getIdRol()); // ← CORREGIDO
    claims.put("correo", usuario.getCorreo());

    return Jwts.builder()
        .claims(claims)
        .subject(usuario.getCorreo())
        .issuer("utp")
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * Obtener los claims del token
   */
  private Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey()) // ✅ Ahora usa un SecretKey válido
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Obtiene el nombre del usuario
   */
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Obtiene la fecha de expiración
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Verificar si el token ha expirado
   */
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Método genérico para extraer cualquier información del token
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getClaims(token);
    return claimsResolver.apply(claims);
  }

}
