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
	 * Creo que deberia hacer una clase para resolver esto, asi queda mas clean
	 * @return
	 */
	public ArrayList<Oferta> floydMarshall(){
		ArrayList<Oferta> ofertas = gestorArchivos.cargarOfertas();
		int cantOfertas = ofertas.size();

		if(!(hayOfertas(cantOfertas, ofertas))) {
			 int [][] nuevaMatriz = new int[cantOfertas][cantOfertas];
			 return new ArrayList<>();
		}
		
		int [][] matrizMaxGanancia = new int [cantOfertas][cantOfertas];
		
		matrizMaxGanancia = inicializarMatriz(matrizMaxGanancia, cantOfertas);
		
		ofertas.sort(Comparator.comparingInt(o -> o.getHoraInicio()));
		
		matrizMaxGanancia = crearAristasEntreOfertasQueNoSeSuperponen(matrizMaxGanancia, cantOfertas);
		
		matrizMaxGanancia = algoritmoFloyMarshallParaMaximizarGanancia(matrizMaxGanancia,cantOfertas);
		
		ArrayList<Oferta> mejoresOfertas = new ArrayList<>();
		
		for (int i = 0; i < ofertas.size(); i++) {
		    for (int j = i + 1; j < ofertas.size(); j++) {
		        if (!seSuperponen(ofertas.get(i), ofertas.get(j))) { 
		            if (matrizMaxGanancia[i][j] > 0) {
		                mejoresOfertas.add(ofertas.get(i));
		                mejoresOfertas.add(ofertas.get(j));
		            }
		        }
		    }
		}

		return mejoresOfertas;
	}
	
	private boolean seSuperponen(Oferta a, Oferta b) {
		 return a.getHoraInicio() < b.getHoraFin() && b.getHoraInicio() < a.getHoraFin();
	}
	
	private int [][] algoritmoFloyMarshallParaMaximizarGanancia(int [][] matrizMaxGanancia, int cantOfertas){
		
		for (int k = 0; k < cantOfertas; k++) {
	        for (int i = 0; i < cantOfertas; i++) {
	            for (int j = 0; j < cantOfertas; j++) {
	                if (matrizMaxGanancia[i][k] != 0 && matrizMaxGanancia[k][j] != 0) {
	                    matrizMaxGanancia[i][j] = Math.max(matrizMaxGanancia[i][j], matrizMaxGanancia[i][k] + matrizMaxGanancia[k][j]);
	                }
	            }
	        }
	    }
		
		return matrizMaxGanancia;
	}
	
	
	private boolean hayOfertas(int cantOfertas,ArrayList<Oferta> ofertas) {
		if(cantOfertas != 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	private int [][] inicializarMatriz(int [][]matrizMaxGanancia, int cantOfertas){
		int [][] nuevaMatriz = new int [cantOfertas][cantOfertas];
		
		for (int i = 0; i < cantOfertas; i++) {
	        for (int j = 0; j < cantOfertas; j++) {
	            nuevaMatriz[i][j] = (i == j) ? ofertas.get(i).getMonto() : 0;
	        }
	    }
		
		return nuevaMatriz;
	}
	
	private int [][] crearAristasEntreOfertasQueNoSeSuperponen(int [][] matrizMaxGanancia, int cantOfertas){
		int [][] nuevaMatriz = new int [cantOfertas][cantOfertas];
		
	    for (int i = 0; i < cantOfertas; i++) {
	        for (int j = i + 1; j < cantOfertas; j++) {
	            if (ofertas.get(i).getHoraFin() <= ofertas.get(j).getHoraInicio()) {
	                nuevaMatriz[i][j] = ofertas.get(j).getMonto();
	            }
	        }
	    }
		
		
		return nuevaMatriz;
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
