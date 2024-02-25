package com.jarellano.entities;

import javax.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "ventas"
)
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(
            name = "id_cliente"
    )
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(
            name = "id_empleado"
    )
    private Empleado empleado;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "fecha_venta")
    private Date fechaVenta;

    @Column(name = "total_venta")
    private Double totalVenta;

    @OneToMany(
            mappedBy = "venta"
    )
    private List<DetalleVenta> detallesVenta;
}
