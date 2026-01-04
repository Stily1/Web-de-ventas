package com.tienda.ventas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.ventas.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVentaId(Long ventaId);
}
