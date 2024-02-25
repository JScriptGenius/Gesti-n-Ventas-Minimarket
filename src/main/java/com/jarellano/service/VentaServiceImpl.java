package com.jarellano.service;

import com.jarellano.entities.Venta;
import com.jarellano.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta saveVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> findVentaById(Long idVenta) {
        return ventaRepository.findById(idVenta);
    }

    @Override
    public String generarNumeroSerie() {
        int numero = 0;
        String numeroUnido = "";
        List<Venta> ventas = findAllVentas();
        List<Integer> numeros = new ArrayList<Integer>();

        ventas.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumeroSerie())));

        if (ventas.isEmpty()) {
            numero = 1;
        }else {
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if (numero < 10) {
            numeroUnido = "000000000"+String.valueOf(numero);
        } else if (numero < 100) {
            numeroUnido = "00000000"+String.valueOf(numero);
        } else if (numero < 1000) {
            numeroUnido = "0000000"+String.valueOf(numero);
        } else if (numero < 10000) {
            numeroUnido = "000000"+String.valueOf(numero);
        }
        return numeroUnido;
    }
}
