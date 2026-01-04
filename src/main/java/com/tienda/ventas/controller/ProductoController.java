package com.tienda.ventas.controller;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.tienda.ventas.model.Producto;
import com.tienda.ventas.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("producto", new Producto());
        return "productos";
    }


    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        return "producto_editar";
    }


    @PostMapping("/productos/actualizar")
    public String actualizar(@Valid Producto producto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            return "producto_editar";
        }

        productoService.guardar(producto);
        return "redirect:/productos";
    }

    @PostMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return "redirect:/productos";
    }
    @PostMapping("/productos")
    public String guardar(@Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listarTodos());
            model.addAttribute("producto", producto);
            return "productos";
        }
        productoService.guardar(producto);
        return "redirect:/productos";

    }
    @GetMapping("/productos/{id}/stock")
    public String formStock(@PathVariable Long id, Model model) {
        Producto p = productoService.buscarPorId(id);
        model.addAttribute("producto", p);
        return "stock"; // stock.html
    }

    @PostMapping("/productos/{id}/stock")
    public String guardarStock(@PathVariable Long id,
                               @RequestParam int cantidad) {
        productoService.agregarStock(id, cantidad);
        return "redirect:/productos";
    }
    @GetMapping("/403")
    public String error403() {
        return "403";
    }


}
