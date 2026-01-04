package com.tienda.ventas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.tienda.ventas.dto.ItemCarrito;
import com.tienda.ventas.model.Producto;
import com.tienda.ventas.service.ProductoService;
import com.tienda.ventas.service.VentaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class VentaController {
    private final ProductoService productoService;
    private final VentaService ventaService;


    public VentaController(ProductoService productoService, VentaService ventaService) {
        this.productoService = productoService;
        this.ventaService = ventaService;
    }
    // ======= Pantalla "Nueva venta" con carrito =======
    @GetMapping("/ventas/nueva")
    public String nuevaVenta(Model model, HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("carrito", carrito);
        model.addAttribute("totalCarrito", totalCarrito(carrito));
        return "venta";
    }



    // ======= Agregar al carrito =======
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam Long productoId,
                                   @RequestParam int cantidad,
                                   HttpSession session) {

        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Producto p = productoService.buscarPorId(productoId);

        List<ItemCarrito> carrito = obtenerCarrito(session);

        // Si ya existe el producto en el carrito, sumamos cantidad
        for (ItemCarrito item : carrito) {
            if (item.getProductoId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + cantidad);
                session.setAttribute("carrito", carrito);
                return "redirect:/ventas/nueva";
            }
        }

        // Si no existe, lo agregamos
        carrito.add(new ItemCarrito(p.getId(), p.getNombre(), p.getPrecio(), cantidad));
        session.setAttribute("carrito", carrito);

        return "redirect:/ventas/nueva";
    }

    // ======= Quitar item del carrito =======
    @PostMapping("/carrito/quitar")
    public String quitarDelCarrito(@RequestParam Long productoId, HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);
        carrito.removeIf(i -> i.getProductoId().equals(productoId));
        session.setAttribute("carrito", carrito);
        return "redirect:/ventas/nueva";
    }

    // ======= Confirmar venta =======
    @PostMapping("/carrito/confirmar")
    public String confirmar(HttpSession session) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        // Guarda venta + detalles + descuenta stock (tu VentaService)
        ventaService.confirmarCarrito(carrito);

        // Vaciar carrito
        session.setAttribute("carrito", new ArrayList<ItemCarrito>());

        return "redirect:/ventas";
    }

    // ======= Helpers =======
    @SuppressWarnings("unchecked")
    private List<ItemCarrito> obtenerCarrito(HttpSession session) {
        Object obj = session.getAttribute("carrito");
        if (obj == null) {
            List<ItemCarrito> nuevo = new ArrayList<>();
            session.setAttribute("carrito", nuevo);
            return nuevo;
        }
        return (List<ItemCarrito>) obj;
    }

    private double totalCarrito(List<ItemCarrito> carrito) {
        double total = 0;
        for (ItemCarrito i : carrito) {
            total += i.getSubtotal();
        }
        return total;
    }
    @GetMapping("/ventas")
    public String historial(Model model) {
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("totalVendido", ventaService.totalVendido());
        return "ventas"; // ventas.html
    }
    @GetMapping("/ventas/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("venta", ventaService.buscarVentaPorId(id));
        model.addAttribute("detalles", ventaService.listarDetalles(id));
        return "venta_detalle";
    }
    @PostMapping("/ventas/vaciar")
    public String vaciarHistorial() {
        ventaService.vaciarHistorial();
        return "redirect:/ventas";
    }
    @PostMapping("/ventas/{id}/eliminar")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return "redirect:/ventas";
    }

}

