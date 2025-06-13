package com.utp.technology.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nombre", nullable = false, length = 50)
  private String nombre;

  @Column(name = "imagen", nullable = false, length = 200)
  private String imagen;

  @Column(name = "precio", nullable = false)
  private Double precio;

  @Column(name = "stock", nullable = false)
  private Integer stock;

}
