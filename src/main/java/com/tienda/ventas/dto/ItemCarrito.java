package com.tienda.ventas.dto;

public class ItemCarrito {
    private Long productoId;
    private String nombre;
    private double precioUnitario;
    private int cantidad;

    public ItemCarrito() {}

    public ItemCarrito(Long productoId, String nombre, double precioUnitario, int cantidad) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public Long getProductoId() { return productoId; }
    public String getNombre() { return nombre; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return precioUnitario * cantidad; }
}
