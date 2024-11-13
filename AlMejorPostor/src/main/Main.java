package main;


import logica.GestorOfertas;
import vista.ControladorEventos;
import vista.InterfazPrincipal;


/**
 * Inicia la aplicacion
 * @author Ale, Ceci, Cris
 *
 */
public class Main {
	
	public static void main(String[] args) {
		GestorOfertas gestorPedidos = new GestorOfertas();
		InterfazPrincipal vista = new InterfazPrincipal();

		ControladorEventos control = new ControladorEventos(vista,gestorPedidos); 

		vista.mostrarPantalla();
	}
}
