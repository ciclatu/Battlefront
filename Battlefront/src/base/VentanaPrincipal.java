package base;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * Clase VentanaPrincipal. En ella se pinta el juego.
 * @author jesusredondogarcia
 *
 */
public class VentanaPrincipal {
	
	//Sigo teniendo la ventana
	JFrame ventana;
	PanelJuego panelJuego;
	
	
	
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 800, 600);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Método que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){
		//Definimos el layout:
		ventana.setLayout(new GridLayout(1,1));
		
		//PANEL JUEGO
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}
	
	/**
	 * Método que inicializa todos los listeners del programa.
	 */
	public void inicializarListeners(){
		
	}
		
	
	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		ventana.setUndecorated(false);
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
}
