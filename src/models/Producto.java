
package models;

public class Producto {
    
    private String nombre;
    private String categoria;
    private int cantidad;
    private double precio;

    public Producto(String nombre, String categoria, int cantidad, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // --- Getters y Setters (Para leer y EDITAR la info) ---

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    // --- Lógica para el botón Vender ---

    // Le restamos al stock lo que se venda
    public boolean vender(int cantidadVendida) {
        if (this.cantidad >= cantidadVendida) {
            this.cantidad -= cantidadVendida;
            return true; // Breve, sí alcanzó el stock y se vendió
        }
        return false; // Paila, no hay suficientes unidades para vender
    }
}