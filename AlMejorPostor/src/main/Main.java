package main;

import gestionDeArchivos.GestorDeArchivos;
import logica.Oferta;

/**
 * Inicia la aplicacion
 * @author Ale, Cris, Ceci
 *
 */
public class Main {

	public static void main(String[] args) {
		
		prueba();
	}
	
	private static void prueba() {
		GestorDeArchivos gestorArchivos = new GestorDeArchivos();
		gestorArchivos.inicializarArchivo();
		
		//Oferta oferta = new Oferta("Alejandro", 7, 8, 13000);
		
		//gestorArchivos.guardarOferta(oferta);
		gestorArchivos.mostrarOfertas();
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		gestorArchivos.eliminarOferta("Alejandro");
		gestorArchivos.mostrarOfertas();
		
		
		
	}

}
