package com.utp.technology.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "comprobantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "tipo_comprobante", nullable = false, length = 20)
    private String tipoComprobante;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @Column(name = "total", nullable = false)
    private Double total;
}
