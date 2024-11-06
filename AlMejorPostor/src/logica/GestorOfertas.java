package logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

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
	
	
	/**
	 * Metodo que se utiliza para adjudicar polinomialmente las mejores ofertas.
	 * @return
	 */
	public ArrayList<Oferta> adjudicacionPolinomica(){
		ArrayList<Oferta> ofertas = gestorArchivos.cargarOfertas();
		return AdjudicacionPolinomial.floydWarshall(ofertas);
	}
	
	/**
	 * METODO GOLOSO PARA LA ADJUDICACION DE LOS MEJORES HORARIOS
	 * @return
	 */
	public ArrayList<Oferta> adjudicacionGolosa(){
		ArrayList<Oferta> todasLasOfertas = obtenerOfertas();
		System.out.println("ANTES Todas las ofertas: " + todasLasOfertas.toString());
		
		todasLasOfertas.sort(Comparator.comparing(Oferta::obtenerPrecioPorHora).reversed());
		
		System.out.println("Despues todas las ofertas: " + todasLasOfertas.toString());
		
		ArrayList <Oferta> ofertasSeleccionadas = new ArrayList<>();
		
		int ultimaHoraFin = 0;
		
		for(Oferta oferta : todasLasOfertas) {
			if(oferta.getHoraInicio() >= ultimaHoraFin) {
				ofertasSeleccionadas.add(oferta);
				ultimaHoraFin = oferta.getHoraFin();
			}
		}
		
		return ofertasSeleccionadas;
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
	
	public ArrayList<Oferta> obtenerOfertas() {
		return this.ofertas;
	}
	
	public void eliminarOFerta(Oferta ofertaAEliminar) {
		if(ofertas.contains(ofertaAEliminar)) {
			gestorArchivos.eliminarOferta(ofertaAEliminar.getNombreOferente());
		}
	}
	
	public void borrarTodo() {
		gestorArchivos.borrarTodo();
	}


}
