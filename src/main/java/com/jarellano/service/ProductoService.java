package com.jarellano.service;

import com.jarellano.entities.Producto;

import java.util.*;

public interface ProductoService {
    Producto saveProducto(Producto producto);
    Optional<Producto> findProductoById(Long id);
    void updateProducto(Producto producto);
}
