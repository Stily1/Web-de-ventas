package com.tienda.ventas.service;


import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Service;
import com.tienda.ventas.model.Producto;
import com.tienda.ventas.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    public List<Producto> listarTodos() {
        return productoRepository.findByActivoTrue();
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Producto no encontrado"));
    }
    public void eliminarPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        p.setActivo(false);
        productoRepository.save(p);
    }

    public void desactivar(Long id){
        Producto p = buscarPorId(id);
        p.setActivo(false);
        productoRepository.save(p);
    }
    public void agregarStock(Long id, int cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        p.setStock(p.getStock() + cantidad);
        productoRepository.save(p);
    }



}
