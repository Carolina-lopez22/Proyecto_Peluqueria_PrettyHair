package edu.umg.peluqueria;
import javax.swing.SwingUtilities;
import edu.umg.peluqueria.ui.VentanaPrincipal;

public class MainUI {
	    public static void main(String[] args) {

	        SwingUtilities.invokeLater(() -> {
	            VentanaPrincipal ventana = new VentanaPrincipal();
	            ventana.setVisible(true);
	        });
	    }
	}

