package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Skate;
import models.DetalleVenta;
import views.VistaSkate;
import utils.GeneradorPDF;

public class ControladorSkate implements ActionListener {
    private VistaSkate vista;
    private ArrayList<Skate> listaInventario;

    public ControladorSkate(VistaSkate vista) {
        this.vista = vista;
        this.listaInventario = new ArrayList<>();
        this.vista.escucharBotones(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregar) {
            try {
                String id = vista.txtId.getText();
                String articulo = vista.txtArticulo.getText();
                String marca = vista.txtMarca.getText();
                double precio = Double.parseDouble(vista.txtPrecio.getText());
                int stock = Integer.parseInt(vista.txtStock.getText());

                listaInventario.add(new Skate(id, articulo, marca, precio, stock));
                actualizarTabla();
                vista.limpiarCampos();
                JOptionPane.showMessageDialog(vista, "¡Agregado al inventario!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Revisa los números de precio y stock.");
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            int fila = vista.tablaInventario.getSelectedRow();
            if (fila >= 0) {
                listaInventario.remove(fila);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Selecciona un producto en la tabla.");
            }
        }

        if (e.getSource() == vista.btnVender) {
            int[] filas = vista.tablaInventario.getSelectedRows();
            if (filas.length > 0) {
                List<DetalleVenta> carrito = new ArrayList<>();
                for (int fila : filas) {
                    Skate s = listaInventario.get(fila);
                    String input = JOptionPane.showInputDialog(vista, "¿Cuántos '" + s.getArticulo() + "' vas a vender?");
                    
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int cant = Integer.parseInt(input);
                            if (cant > 0 && s.getStock() >= cant) {
                                carrito.add(new DetalleVenta(s, cant));
                            } else {
                                JOptionPane.showMessageDialog(vista, "Stock insuficiente de " + s.getArticulo());
                            }
                        } catch (Exception ex) {}
                    }
                }
                
                if (!carrito.isEmpty()) {
                    for (DetalleVenta dv : carrito) {
                        dv.getSkate().setStock(dv.getSkate().getStock() - dv.getCantidad());
                    }
                    actualizarTabla();
                    GeneradorPDF.crearFactura(carrito);
                    JOptionPane.showMessageDialog(vista, "¡Venta lista! PDF generado.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Selecciona qué vas a vender.");
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel mod = (DefaultTableModel) vista.tablaInventario.getModel();
        mod.setRowCount(0); 
        for (Skate s : listaInventario) {
            mod.addRow(new Object[]{s.getId(), s.getArticulo(), s.getMarca(), s.getPrecio(), s.getStock()});
        }
    }
}