package com.tienda.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.ventas.model.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findAllByOrderByFechaDescIdDesc();
    List<Venta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v")
    BigDecimal totalGeneral();

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha >= :inicio AND v.fecha < :fin")
    BigDecimal totalEntre(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}

