package com.jarellano.service;

import com.jarellano.entities.Empleado;

import java.util.*;

public interface EmpleadoService {
    Empleado saveEmpleado(Empleado empleado);
    Optional<Empleado> findEmpleadoById(Long id);
    Optional<Empleado> findEmpleadoByDni(String dni);
    void updateEmpleado(Empleado empleado);
}
