package com.utp.technology.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nombre", nullable = false, length = 50)
  private String nombre;

  @Column(name = "correo", nullable = false, unique = true, length = 100)
  private String correo;

  @Column(name = "clave", nullable = false, length = 255)
  private String clave;

  @ManyToOne
  @JoinColumn(name = "id_rol", nullable = false)
  private Rol rol;
}
