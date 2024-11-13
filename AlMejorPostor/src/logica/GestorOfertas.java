package logica;

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
	 * METODO GOLOSO PARA LA ADJUDICACION DE LOS MEJORES HORARIOS
	 * @return
	 */
	public ArrayList<Oferta> adjudicacionGolosa(String fecha){
		ArrayList<Oferta> todasLasOfertas = obtenerOfertasDeEstaFecha(fecha);
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

	public boolean esNuevaOferta(Oferta nuevaOferta) {
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
