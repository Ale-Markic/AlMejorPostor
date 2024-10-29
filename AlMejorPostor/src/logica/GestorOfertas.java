package logica;

import java.util.ArrayList;

import gestionDeArchivos.GestorDeArchivos;

/**
 * Clase principal
 *
 */
public class GestorOfertas {

	private ArrayList<Oferta> ofertas; // ArrayList porque el recorrido es mas rápido.
	private GestorDeArchivos gestorArchivos = new GestorDeArchivos();


	public GestorOfertas() {
		this.gestorArchivos = new GestorDeArchivos();
		this.gestorArchivos.inicializarArchivo();
		this.ofertas = gestorArchivos.cargarOfertas();
	}

	public void agregarOferta(Oferta nuevaOferta) {
		if(esNuevaOferta(nuevaOferta)) {
			gestorArchivos.agregarOferta(nuevaOferta);
		}
	}
	
	private boolean esNuevaOferta(Oferta nuevaOferta) {
		boolean esNuevaOferta = true;
		
		for(Oferta oferta : ofertas){
			if(oferta.equals(nuevaOferta)) {
				esNuevaOferta = false;
			}
		}
		
		return esNuevaOferta;	
	}
	
	public ArrayList<Oferta> obtenerOFertas() {
		return ofertas;
	}
	
	public void eliminarOFerta(Oferta ofertaAEliminar) {
		if(ofertas.contains(ofertaAEliminar)) {
			gestorArchivos.eliminarOferta(ofertaAEliminar.getNombreOferente());
		}
	}



}
