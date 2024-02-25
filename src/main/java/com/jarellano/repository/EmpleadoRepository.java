package com.jarellano.repository;

import com.jarellano.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query(" SELECT E FROM Empleado E WHERE E.estado = 1 ")
    List<Empleado> findAllEmpleados();

    @Transactional
    @Modifying
    @Query(" UPDATE Empleado E SET E.estado = 0 WHERE E.idEmpleado = :idEmpleado ")
    void disableEmpleado(@Param("idEmpleado") Long idEmpleado);

    @Query(" SELECT E FROM Empleado E WHERE E.dni = :dni ")
    Optional<Empleado> findEmpleadoByDni(@Param("dni") String dni);
}
