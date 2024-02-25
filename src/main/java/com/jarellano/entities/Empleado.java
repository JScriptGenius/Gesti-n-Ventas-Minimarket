package com.jarellano.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "empleados"
)
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long idEmpleado;
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String usuario;
    private String password;
    private int estado;
}
