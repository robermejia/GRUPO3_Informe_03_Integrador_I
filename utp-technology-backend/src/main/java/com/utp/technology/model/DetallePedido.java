package com.utp.technology.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
}
