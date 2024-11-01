package main;

import java.util.ArrayList;

import gestionDeArchivos.GestorDeArchivos;
import logica.GestorOfertas;
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
		
		/*
		GestorOfertas gestorOfertas = new GestorOfertas();
		
		gestorOfertas.borrarTodo();
		//gestorArchivos.inicializarArchivo();
		
		Oferta oferta1 = new Oferta("Oferta 1", 15, 19, 1);
		Oferta oferta2 = new Oferta("Oferta 2", 1, 23, 99);
		Oferta oferta3 = new Oferta("Oferta 3", 3, 6, 13);
		Oferta oferta4 = new Oferta("Oferta 4", 14, 20, 130);
		Oferta oferta5 = new Oferta("Oferta 5", 12, 19, 300);
		Oferta oferta6 = new Oferta("Oferta 6", 7, 10, 30);
		Oferta oferta7 = new Oferta("Oferta 7", 5, 8, 10);
		Oferta oferta8 = new Oferta("Oferta 8", 4, 19, 300);
		Oferta oferta9 = new Oferta("Oferta 9", 17, 18, 100);
		Oferta oferta10 = new Oferta("Oferta 10", 4, 8, 1300);
		
		gestorOfertas.agregarOferta(oferta1);
		gestorOfertas.agregarOferta(oferta2);
		gestorOfertas.agregarOferta(oferta3);
		gestorOfertas.agregarOferta(oferta4);
		gestorOfertas.agregarOferta(oferta5);
		gestorOfertas.agregarOferta(oferta6);
		gestorOfertas.agregarOferta(oferta7);
		gestorOfertas.agregarOferta(oferta8);
		gestorOfertas.agregarOferta(oferta9);
		gestorOfertas.agregarOferta(oferta10);
		
		ArrayList <Oferta> mejor = gestorOfertas.adjudicacionGolosa();
		System.out.println(mejor.toString());
		
		*/
		//gestorArchivos.guardarOferta(oferta);
		//gestorArchivos.mostrarOfertas();
		
		
		GestorDeArchivos gestorArchivo = new GestorDeArchivos();
		
		gestorArchivo.mostrarOfertas();
		gestorArchivo.borrarTodo();
		gestorArchivo.mostrarOfertas();
		
	}

}
