package com.jarellano.service;

import com.jarellano.entities.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmpleadoServiceTest {

    @Autowired
    private EmpleadoService empleadoService;

    @Test
    public void registrarEmpleado() {
        Empleado empleado = Empleado.builder()
                .dni("74713198")
                .nombre("Jhosep Bernabe")
                .apellido("Arellano Castillo")
                .direccion("Los rosales calle 1")
                .telefono("974523734")
                .usuario("jos")
                .estado(1)
                .build();
        empleadoService.saveEmpleado(empleado);
    }
}