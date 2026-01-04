package com.tienda.ventas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.ventas.dto.ItemCarrito;
import com.tienda.ventas.model.DetalleVenta;
import com.tienda.ventas.model.Producto;
import com.tienda.ventas.model.Venta;
import com.tienda.ventas.repository.DetalleVentaRepository;
import com.tienda.ventas.repository.ProductoRepository;
import com.tienda.ventas.repository.VentaRepository;

@Service
@Transactional
public class VentaService {

    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public VentaService(ProductoRepository productoRepository,
                        VentaRepository ventaRepository,
                        DetalleVentaRepository detalleVentaRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    // =========================
    // HISTORIAL
    // =========================
    public List<Venta> listarVentas() {
        return ventaRepository.findAllByOrderByFechaDescIdDesc();
    }

    public double totalVendido() {
        return listarVentas()
                .stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    // =========================
    // CARRITO: CONFIRMAR VENTA
    // =========================
    public Venta confirmarCarrito(List<ItemCarrito> carrito) {

        if (carrito == null || carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // 1) Validar cantidades y stock ANTES de descontar
        for (ItemCarrito item : carrito) {
            if (item.getCantidad() <= 0) {
                throw new RuntimeException("Cantidad inválida para: " + item.getNombre());
            }

            Producto p = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));

            if (p.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + p.getNombre());
            }
        }

        // 2) Crear venta (cabecera)
        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setTotal(0);
        venta = ventaRepository.save(venta);

        double total = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        // 3) Descontar stock + crear items (detalle)
        for (ItemCarrito item : carrito) {
            Producto p = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));

            // Descontar stock
            p.setStock(p.getStock() - item.getCantidad());
            productoRepository.save(p);

            // Crear detalle
            DetalleVenta dv = new DetalleVenta();
            dv.setVenta(venta);
            dv.setProducto(p);
            dv.setCantidad(item.getCantidad());
            dv.setPrecioUnitario(p.getPrecio());
            dv.setSubtotal(p.getPrecio() * item.getCantidad());

            total += dv.getSubtotal();
            detalles.add(dv);
        }

        // 4) Guardar detalles + actualizar total de venta
        detalleVentaRepository.saveAll(detalles);
        venta.setTotal(total);
        return ventaRepository.save(venta);
    }public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public List<DetalleVenta> listarDetalles(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    @Transactional
    public void vaciarHistorial() {
        detalleVentaRepository.deleteAll();
        ventaRepository.deleteAll();
    }
    @Transactional
    public void eliminarVenta(Long ventaId) {
        detalleVentaRepository.deleteAll(detalleVentaRepository.findByVentaId(ventaId));
        ventaRepository.deleteById(ventaId);
    }


}
