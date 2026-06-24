package inventarioone;

import controller.ControladorSkate;
import views.VistaSkate;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VistaSkate vista = new VistaSkate();
                ControladorSkate controlador = new ControladorSkate(vista);
                vista.setVisible(true);
            }
        });
    }
}