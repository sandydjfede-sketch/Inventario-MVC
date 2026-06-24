package models;

public class DetalleVenta {
    private Skate skate;
    private int cantidad;

    public DetalleVenta(Skate skate, int cantidad) {
        this.skate = skate;
        this.cantidad = cantidad;
    }

    public Skate getSkate() { return skate; }
    public int getCantidad() { return cantidad; }
    
    public double getSubtotal() { 
        return skate.getPrecio() * cantidad; 
    }
}