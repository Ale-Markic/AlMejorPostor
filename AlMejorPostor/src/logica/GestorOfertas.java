package logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

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
		return AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
	}

	/**
	 * METODO GOLOSO PARA LA ADJUDICACION DE LOS MEJORES HORARIOS
	 * @return
	 */
	public ArrayList<Oferta> adjudicacionGolosa(){
		ArrayList<Oferta> todasLasOfertas = obtenerOfertas();
		todasLasOfertas.sort(Comparator.comparing(Oferta::obtenerPrecioPorHora).reversed());
		
		ArrayList <Oferta> ofertasSeleccionadas = new ArrayList<>();

		for(Oferta oferta : todasLasOfertas) {
			if(esOfertaNoSolapada(oferta, ofertasSeleccionadas)) {
				ofertasSeleccionadas.add(oferta);
			}
		}

		return ofertasSeleccionadas;
	}
	
	public boolean esOfertaNoSolapada(Oferta oferta, ArrayList<Oferta> ofertasSeleccionadas) {
		  if (ofertasSeleccionadas.isEmpty()) {
	          return true;
	      }
		  
		  for (Oferta ofertaActual : ofertasSeleccionadas) {
	          if (!(oferta.getHoraFin() <= ofertaActual.getHoraInicio() || 
	                oferta.getHoraInicio() >= ofertaActual.getHoraFin())) {
	              return false;
	          }
	      }
	  
	      return true;
	}



	public void agregarOferta(Oferta nuevaOferta) {
		if(esNuevaOferta(nuevaOferta)) {
			gestorArchivos.agregarOferta(nuevaOferta);
			actualizarArregloDeOfertas();
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
	
	private void actualizarArregloDeOfertas() {
		this.ofertas = gestorArchivos.cargarOfertas();
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

	public ArrayList<Oferta> obtenerOfertasDeEstaFecha(String fecha){
		ArrayList<Oferta> retorno = new ArrayList<>();

		if(this.ofertas == null) {
			return retorno;
		}
		
		return buscarOfertas(fecha, retorno);
	}
	
	private ArrayList<Oferta> buscarOfertas(String fecha, ArrayList<Oferta> retorno){
		for(Oferta oferta : ofertas) {
			if(oferta.getFecha().equals(fecha)) {
				retorno.add(oferta);
			}
		}
		return retorno;
	}
}
