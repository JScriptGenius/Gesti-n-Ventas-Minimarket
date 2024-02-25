package com.jarellano.controller;

import com.jarellano.entities.*;
import com.jarellano.repository.*;
import com.jarellano.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DetalleVentaService detalleVentaService;
    @Autowired
    private EmpleadoService empleadoService;

    List<DetalleVenta> detalleVentaList = new ArrayList<DetalleVenta>();
    Venta venta = new Venta();
    Cliente cliente = new Cliente();

    @GetMapping("/seleccionarProducto")
    public String seleccionarProducto(Model model) {
        model.addAttribute("productos",productoRepository.findAllProductos());
        return "/venta/seleccionarProducto";
    }

    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam String nombre, Model model) {
        List<Producto> productos = productoRepository.findAllProductos()
                .stream().filter(p ->
                        p.getNombre().contains(nombre))
                .collect(Collectors.toList());
        model.addAttribute("productos",productos);
        return "venta/seleccionarProducto";
    }

    @PostMapping("/agregarProductoVenta")
    public String agregarProductoVenta(@RequestParam Long idProducto, @RequestParam Integer cantidad, Model model) {
        DetalleVenta detalleVenta = new DetalleVenta();
        double sumaTotal = 0;

        Producto producto = productoService.findProductoById(idProducto).get();

        detalleVenta.setCantidad(cantidad);
        detalleVenta.setPrecioUnitario(producto.getPrecio());
        detalleVenta.setProducto(producto);
        detalleVenta.setTotalDetalle(producto.getPrecio() * cantidad);

        Long id_producto = producto.getIdProducto();
        boolean ingresado = detalleVentaList.stream().anyMatch(p -> p.getProducto().getIdProducto() == id_producto);

        if (!ingresado) {
            detalleVentaList.add(detalleVenta);
        }

        sumaTotal=detalleVentaList.stream().mapToDouble(DetalleVenta::getTotalDetalle).sum();
        venta.setTotalVenta(sumaTotal);
        model.addAttribute("detalles",detalleVentaList);
        model.addAttribute("venta",venta);
        return "/venta/gestionarProductosVenta";
    }

    @GetMapping("/verProductosAgregadosVenta")
    public String verProductosAgregadosVenta(Model model) {
        model.addAttribute("detalles",detalleVentaList);
        model.addAttribute("venta",venta);
        return "/venta/gestionarProductosVenta";
    }

    @GetMapping("/eliminarProductoVenta/{idProducto}")
    public String eliminarProductoVenta(@PathVariable Long idProducto, Model model) {

        List<DetalleVenta> ventaList = new ArrayList<>();
        for (DetalleVenta detalleVenta : detalleVentaList) {
            if(detalleVenta.getProducto().getIdProducto() != idProducto){
                ventaList.add(detalleVenta);
            }
        }
        detalleVentaList = ventaList;

        double sumaTotal = 0;
        sumaTotal=detalleVentaList.stream().mapToDouble(DetalleVenta::getTotalDetalle).sum();

        venta.setTotalVenta(sumaTotal);
        model.addAttribute("detalles",detalleVentaList);
        model.addAttribute("venta",venta);
        return "/venta/gestionarProductosVenta";
    }

    @GetMapping("/verClientes")
    public String verClientes(Model model) {
        model.addAttribute("clientes",clienteRepository.findAllClientes());
        return "/venta/seleccionarCliente";
    }

    @PostMapping("/searchCliente")
    public String searchCliente(@RequestParam String dni, Model model) {
        List<Cliente> clientes = clienteRepository.findAllClientes()
                .stream().filter(c ->
                        c.getDni().contains(dni))
                .collect(Collectors.toList());
        model.addAttribute("clientes",clientes);
        return "venta/seleccionarCliente";
    }

    @GetMapping("/nuevoCliente")
    public String registrarCliente() {
        return "/venta/registrarCliente";
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(Cliente cliente) {
        cliente.setEstado(1);
        clienteService.saveCliente(cliente);
        return "redirect:/venta/verClientes";
    }

    @GetMapping("/seleccionarCliente/{idCliente}")
    public String seleccionarCliente(@PathVariable Long idCliente ,Model model) {

        cliente = clienteService.findClienteById(idCliente).get();

        model.addAttribute("cliente",cliente);
        model.addAttribute("detalles",detalleVentaList);
        model.addAttribute("venta",venta);
        return "/venta/generarVenta";
    }

    @GetMapping("/generarVenta")
    public String generarVenta(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Date fechaVenta = new Date();
        venta.setFechaVenta(fechaVenta);
        venta.setNumeroSerie(ventaService.generarNumeroSerie());
        venta.setCliente(cliente);

        Empleado empleado = empleadoService.findEmpleadoById(Long.parseLong(session.getAttribute("sid_empleado").toString())).get();
        venta.setEmpleado(empleado);

        ventaService.saveVenta(venta);

        for (DetalleVenta dtv : detalleVentaList) {
            dtv.setVenta(venta);
            detalleVentaService.saveDetalleVenta(dtv);
            /*ACTUALIZAR STOCK*/
            productoRepository.actualizarStock(dtv.getProducto().getStock() - dtv.getCantidad(), dtv.getProducto().getIdProducto());
        }

        venta = new Venta();
        detalleVentaList.clear();
        redirectAttributes.addFlashAttribute("msgConfirmacionOrden","¡Venta registrada!");
        model.addAttribute("productos",productoRepository.findAllProductos());
        return "/venta/seleccionarProducto";
    }
}
