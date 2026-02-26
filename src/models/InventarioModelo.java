
package models;

import java.util.ArrayList;
import java.util.List;

public class InventarioModelo {
    
    private List<Producto> listaDeProductos;

    public InventarioModelo() {
        this.listaDeProductos = new ArrayList<>();
    }

    // Para el botón "Agregar"
    public void agregarProducto(Producto nuevoProducto) {
        listaDeProductos.add(nuevoProducto);
    }

    // Para el botón "Borrar seleccionado"
    public void eliminarProducto(int indiceTabla) {
        // Borramos usando la posición de la fila que el usuario seleccionó
        if(indiceTabla >= 0 && indiceTabla < listaDeProductos.size()) {
            listaDeProductos.remove(indiceTabla);
        }
    }
    
    // Para el botón "Editar"
    public void editarProducto(int indiceTabla, String nuevoNombre, String nuevaCategoria, int nuevaCantidad, double nuevoPrecio) {
        // Buscamos el producto en la posición seleccionada y le chancamos los datos nuevos
        if(indiceTabla >= 0 && indiceTabla < listaDeProductos.size()) {
            Producto productoAEditar = listaDeProductos.get(indiceTabla);
            productoAEditar.setNombre(nuevoNombre);
            productoAEditar.setCategoria(nuevaCategoria);
            productoAEditar.setCantidad(nuevaCantidad);
            productoAEditar.setPrecio(nuevoPrecio);
        }
    }

    // Para el botón "VenderSeleccionado"
    public boolean venderProducto(int indiceTabla, int cantidadAVender) {
        if(indiceTabla >= 0 && indiceTabla < listaDeProductos.size()) {
            Producto producto = listaDeProductos.get(indiceTabla);
            return producto.vender(cantidadAVender);
        }
        return false;
    }

    // Para que la vista pueda leer toda la lista y armar la tabla
    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }
}