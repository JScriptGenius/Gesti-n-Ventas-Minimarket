package com.jarellano.controller;

import com.jarellano.entities.Producto;
import com.jarellano.repository.ProductoRepository;
import com.jarellano.service.ProductoService;
import com.jarellano.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/listaProductos")
    public String listaProductos(Model model) {
        model.addAttribute("productos",productoRepository.findAllProductos());
        return "/producto/listaProductos";
    }

    @GetMapping("/nuevoProducto")
    public String nuevoProducto() {
        return "/producto/registrarProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(Producto producto, @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        /*IMG*/
        if (producto.getIdProducto() == null) {
            String nombreImagen= uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {}
        producto.setEstado(1);
        productoService.saveProducto(producto);
        redirectAttributes.addFlashAttribute("msgConfirmacion","¡Producto registrado correctamente!");
        return "redirect:/producto/listaProductos";
    }

    @GetMapping("/editarProducto/{idProducto}")
    public String editarProducto(@PathVariable Long idProducto, Model model) {
        Producto producto = productoService.findProductoById(idProducto).get();
        model.addAttribute("producto",producto);
        return "/producto/actualizarProducto";
    }

    @PostMapping("/actualizarProducto")
    public String actualizarProducto(@RequestParam("idProducto") Long id, Producto producto, @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        Producto productoBD = productoService.findProductoById(id).get();
        productoBD.setNombre(producto.getNombre());
        productoBD.setDescripcion(producto.getDescripcion());

        if (file.isEmpty()) {
            producto.setImagen(productoBD.getImagen());
        } else {
            if (!productoBD.getImagen().equals("default.png")) {
                uploadFileService.deleteImage(productoBD.getImagen());
            }
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        productoBD.setPrecio(producto.getPrecio());
        productoBD.setStock(producto.getStock());
        producto.setEstado(productoBD.getEstado());
        productoService.updateProducto(producto);
        redirectAttributes.addFlashAttribute("msgConfirmacion","¡Producto actualizado correctamente!");
        return "redirect:/producto/listaProductos";
    }

    @GetMapping("/eliminarProducto/{idProducto}")
    public String eliminarProducto(@PathVariable Long idProducto, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.findProductoById(idProducto).get();
        if (!producto.getImagen().equals("default.png")) {
            uploadFileService.deleteImage(producto.getImagen());
        }
        productoRepository.disableProducto(idProducto);
        redirectAttributes.addFlashAttribute("msgConfirmacion","¡Producto eliminado correctamente!");
        return "redirect:/producto/listaProductos";
    }

}
