package models;

public class Skate {
    private String id;
    private String articulo; // Ej: Tabla, Ruedas, Trucks
    private String marca;
    private double precio;
    private int stock;

    public Skate(String id, String articulo, String marca, double precio, int stock) {
        this.id = id;
        this.articulo = articulo;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getArticulo() { return articulo; }
    public void setArticulo(String articulo) { this.articulo = articulo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
