package com.jarellano.service;

import com.jarellano.entities.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaService {

    List<Venta> findAllVentas();
    Venta saveVenta(Venta venta);
    Optional<Venta> findVentaById(Long idVenta);
    String generarNumeroSerie();
}
