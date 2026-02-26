package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.InventarioModelo;
import models.Producto;
import views.Inventario;

public class InventarioControlador implements ActionListener {

    private Inventario vista;
    private InventarioModelo modelo;

    // Constructor: Aquí conectamos la vista y el modelo
    public InventarioControlador(Inventario vista, InventarioModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Le ponemos las orejas (Listeners) a los botones para que escuchen los clics
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnBorrar.addActionListener(this);
        this.vista.btnVender.addActionListener(this);

        // Esto es un toque coqueto: Cuando usted haga clic en una fila de la tabla, 
        // los datos se pasan automáticamente a las cajas de texto de la izquierda.
        this.vista.jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarDatosEnFormulario();
            }
        });
    }

    // Método para arrancar la ventana
    public void iniciar() {
        vista.setTitle("Gestión de Inventario - TecniSkate");
        vista.setLocationRelativeTo(null); // Para que salga en el centro de la pantalla
        vista.setVisible(true);
    }

    // Aquí es donde pasa la magia cuando usted espicha un botón
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- BOTÓN AGREGAR ---
        if (e.getSource() == vista.btnAgregar) {
            try {
                // Sacamos los datos de la interfaz
                String nombre = vista.txtNombrep.getText();
                String categoria = vista.cmbCate.getSelectedItem().toString();
                int cantidad = (int) vista.spnCant.getValue();
                double precio = Double.parseDouble(vista.txtPrecio.getText());

                // Validamos que el mk no deje el nombre vacío
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "¡Hágale suave! El nombre no puede estar vacío.", "Pilas", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Creamos el producto y lo mandamos al modelo
                Producto nuevoProd = new Producto(nombre, categoria, cantidad, precio);
                modelo.agregarProducto(nuevoProd);
                
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(vista, "¡Coronamos! Producto agregado con éxito.");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "¡Qué huevón! Escriba un precio válido, puros números.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // --- BOTÓN LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }

        // --- BOTÓN EDITAR ---
        if (e.getSource() == vista.btnEditar) {
            int filaSeleccionada = vista.jTable1.getSelectedRow();
            if (filaSeleccionada >= 0) {
                try {
                    String nombre = vista.txtNombrep.getText();
                    String categoria = vista.cmbCate.getSelectedItem().toString();
                    int cantidad = (int) vista.spnCant.getValue();
                    double precio = Double.parseDouble(vista.txtPrecio.getText());

                    modelo.editarProducto(filaSeleccionada, nombre, categoria, cantidad, precio);
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(vista, "Producto actualizado, quedó una chimba.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vista, "Verifique el precio, tiene que ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla para poder editarlo, no sea toche.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }

        // --- BOTÓN BORRAR ---
        if (e.getSource() == vista.btnBorrar) {
            int filaSeleccionada = vista.jTable1.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Seguro que quiere borrar esta vuelta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    modelo.eliminarProducto(filaSeleccionada);
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(vista, "Producto borrado del mapa.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Papi, seleccione algo en la tabla primero para borrar.", "Pilas", JOptionPane.WARNING_MESSAGE);
            }
        }

        // --- BOTÓN VENDER ---
        if (e.getSource() == vista.btnVender) {
            int filaSeleccionada = vista.jTable1.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String input = JOptionPane.showInputDialog(vista, "¿Cuántas unidades va a vender?");
                if (input != null && !input.isEmpty()) {
                    try {
                        int cantAVender = Integer.parseInt(input);
                        boolean ventaExitosa = modelo.venderProducto(filaSeleccionada, cantAVender);
                        
                        if (ventaExitosa) {
                            actualizarTabla();
                            JOptionPane.showMessageDialog(vista, "¡Caja registrada! Venta exitosa.");
                        } else {
                            JOptionPane.showMessageDialog(vista, "¡Paila! No hay tanto stock para vender eso.", "Stock Insuficiente", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(vista, "Ingrese un número entero válido, no invente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione qué va a vender en la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // --- MÉTODOS DE APOYO ---

    // Este método coge los datos del modelo y los pinta en la tabla
    private void actualizarTabla() {
        DefaultTableModel tablaModelo = (DefaultTableModel) vista.jTable1.getModel();
        tablaModelo.setRowCount(0); // Borra las filas viejas para no duplicar

        for (Producto p : modelo.getListaDeProductos()) {
            Object[] fila = new Object[4];
            fila[0] = p.getNombre();
            fila[1] = p.getCategoria();
            fila[2] = p.getCantidad();
            fila[3] = p.getPrecio();
            tablaModelo.addRow(fila);
        }
    }

    // Limpia los textfields después de hacer algo
    private void limpiarCampos() {
        vista.txtNombrep.setText("");
        vista.cmbCate.setSelectedIndex(0);
        vista.spnCant.setValue(0);
        vista.txtPrecio.setText("");
        vista.jTable1.clearSelection();
    }

    // Carga los datos de la fila seleccionada a los cajoncitos para editarlos fácil
    private void cargarDatosEnFormulario() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            vista.txtNombrep.setText(vista.jTable1.getValueAt(filaSeleccionada, 0).toString());
            vista.cmbCate.setSelectedItem(vista.jTable1.getValueAt(filaSeleccionada, 1).toString());
            vista.spnCant.setValue(Integer.parseInt(vista.jTable1.getValueAt(filaSeleccionada, 2).toString()));
            vista.txtPrecio.setText(vista.jTable1.getValueAt(filaSeleccionada, 3).toString());
        }
    }
}