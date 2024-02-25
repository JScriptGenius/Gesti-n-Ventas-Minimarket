package com.jarellano.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "detalle_ventas"
)
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(
            name = "id_venta"
    )
    private Venta venta;

    @ManyToOne
    @JoinColumn(
            name = "id_producto"
    )
    private Producto producto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "total_detalle")
    private Double totalDetalle;
}
