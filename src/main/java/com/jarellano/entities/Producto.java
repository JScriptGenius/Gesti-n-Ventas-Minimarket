package com.jarellano.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "productos"
)
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private int stock;
    private int estado;
}
