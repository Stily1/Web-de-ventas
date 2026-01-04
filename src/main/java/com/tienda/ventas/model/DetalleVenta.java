package com.tienda.ventas.model;


import jakarta.persistence.*;

@Entity
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Venta venta;

    @ManyToOne(optional = false)
    private Producto producto;

    private int cantidad;
    private double precioUnitario;
    private double subtotal;


    public DetalleVenta() {
    }
    public Long getId() {
        return id;
    }
    public Venta getVenta(){
        return venta;
    }
    public void setVenta(Venta venta){
        this.venta = venta;
    }
    public Producto getProducto(){
        return producto;
    }
    public void setProducto(Producto producto){
        this.producto = producto;
    }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
