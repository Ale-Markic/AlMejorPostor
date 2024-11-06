package logica;

import java.util.ArrayList;
import java.util.Comparator;

public class AdjudicacionPolinomial {

	public static ArrayList<Oferta> floydWarshall(ArrayList<Oferta> ofertas){
		int cantOfertas = ofertas.size();

		if(!(hayOfertas(cantOfertas, ofertas))) {
			int [][] nuevaMatriz = new int[cantOfertas][cantOfertas];
			return new ArrayList<>();
		}

		int [][] matrizMaxGanancia = new int [cantOfertas][cantOfertas];

		matrizMaxGanancia = inicializarMatriz(matrizMaxGanancia, cantOfertas, ofertas);
		ofertas.sort(Comparator.comparingInt(o -> o.getHoraInicio()));
		matrizMaxGanancia = crearAristasEntreOfertasQueNoSeSuperponen(matrizMaxGanancia, cantOfertas, ofertas);
		matrizMaxGanancia = algoritmoFloydWarshallParaMaximizarGanancia(matrizMaxGanancia,cantOfertas);

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


	private static boolean seSuperponen(Oferta a, Oferta b) {
		return a.getHoraInicio() < b.getHoraFin() && b.getHoraInicio() < a.getHoraFin();
	}

	private static int [][] algoritmoFloydWarshallParaMaximizarGanancia(int [][] matrizMaxGanancia, int cantOfertas){

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


	private static boolean hayOfertas(int cantOfertas,ArrayList<Oferta> ofertas) {
		if(cantOfertas != 0) {
			return true;
		}else {
			return false;
		}

	}

	private static int [][] inicializarMatriz(int [][]matrizMaxGanancia, int cantOfertas, ArrayList<Oferta> ofertas){
		int [][] nuevaMatriz = new int [cantOfertas][cantOfertas];

		for (int i = 0; i < cantOfertas; i++) {
			for (int j = 0; j < cantOfertas; j++) {
				nuevaMatriz[i][j] = (i == j) ? ofertas.get(i).getMonto() : 0;
			}
		}
		return nuevaMatriz;
	}

	private static int [][] crearAristasEntreOfertasQueNoSeSuperponen(int [][] matrizMaxGanancia, int cantOfertas, ArrayList<Oferta> ofertas){
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


}
