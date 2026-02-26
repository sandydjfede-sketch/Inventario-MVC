
package inventarioone;

import controllers.InventarioControlador;
import models.InventarioModelo;
import views.Inventario;

public class Inventarioone {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
Inventario vista = new Inventario();
InventarioModelo modelo = new InventarioModelo();

// Se los pasa al controlador
InventarioControlador controlador = new InventarioControlador(vista, modelo);

// Arranca la vuelta
controlador.iniciar();
    }
    
}// Instancia la vista y el modelo

