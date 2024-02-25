package com.jarellano.repository;

import com.jarellano.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(" SELECT P FROM Producto P WHERE P.estado = 1 ")
    List<Producto> findAllProductos();

    @Transactional
    @Modifying
    @Query(" UPDATE Producto P SET P.estado = 0 WHERE P.idProducto = :idProducto ")
    void disableProducto(@Param("idProducto") Long idProducto);

    /*ACTUALIZAR STOCK*/
    @Transactional
    @Modifying
    @Query(
            value = "UPDATE productos SET stock = ?1 WHERE id_producto = ?2",
            nativeQuery = true
    )
    void actualizarStock(int cantidad, Long idProducto);
}
