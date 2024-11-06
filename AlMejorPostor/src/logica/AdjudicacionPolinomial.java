package logica;

import java.util.ArrayList;


public class AdjudicacionPolinomial {

	public static ArrayList<Oferta> obtenerMejoresOfertas(ArrayList<Oferta> ofertas) {
	    int n = ofertas.size();
	    if (n == 0) return new ArrayList<>();

	    int[][] matrizGanancia = new int[n][n];
	    int[][] matrizRastreo = new int[n][n];

	    inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);
	    aplicarFloydWarshall(ofertas, matrizGanancia, matrizRastreo);

	    int[] indicesMaxGanancia = encontrarMaxGanancia(matrizGanancia);
	    int start = indicesMaxGanancia[0];
	    int end = indicesMaxGanancia[1];

	    ArrayList<Oferta> mejoresOfertas = new ArrayList<>();
	    reconstruirCamino(matrizRastreo, ofertas, mejoresOfertas, start, end);

	    return mejoresOfertas;
	}
	
	public static void inicializarMatrices(ArrayList<Oferta> ofertas, int[][] matrizGanancia, int[][] matrizRastreo) {
	    int n = ofertas.size();
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (i == j) {
	                matrizGanancia[i][j] = ofertas.get(i).getMonto();
	                matrizRastreo[i][j] = -1;
	            } else if (ofertas.get(i).getHoraFin() <= ofertas.get(j).getHoraInicio()) {
	                matrizGanancia[i][j] = ofertas.get(j).getMonto();
	                matrizRastreo[i][j] = i;
	            } else {
	                matrizGanancia[i][j] = 0;
	                matrizRastreo[i][j] = -1;
	            }
	        }
	    }
	}

	
	public static void aplicarFloydWarshall(ArrayList<Oferta> ofertas, int[][] matrizGanancia, int[][] matrizRastreo) {
	    int n = ofertas.size();
	    for (int k = 0; k < n; k++) {
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                if (matrizGanancia[i][k] != 0 && matrizGanancia[k][j] != 0 
	                        && ofertas.get(i).getHoraFin() <= ofertas.get(k).getHoraInicio()
	                        && ofertas.get(k).getHoraFin() <= ofertas.get(j).getHoraInicio()) {
	                    int nuevaGanancia = matrizGanancia[i][k] + matrizGanancia[k][j];
	                    if (nuevaGanancia > matrizGanancia[i][j]) {
	                        matrizGanancia[i][j] = nuevaGanancia;
	                        matrizRastreo[i][j] = k;
	                    }
	                }
	            }
	        }
	    }
	}

	
	public static int[] encontrarMaxGanancia(int[][] matrizGanancia) {
	    int maxGanancia = 0;
	    int start = -1, end = -1;
	    int n = matrizGanancia.length;
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (matrizGanancia[i][j] > maxGanancia) {
	                maxGanancia = matrizGanancia[i][j];
	                start = i;
	                end = j;
	            }
	        }
	    }
	    return new int[] { start, end, maxGanancia };
	}


	public static void reconstruirCamino(int[][] matrizRastreo, ArrayList<Oferta> ofertas, ArrayList<Oferta> mejoresOfertas, int start, int end) {
		
	    ArrayList<Oferta> caminoInverso = new ArrayList<>();
	    int actual = end;


	    while (actual != start) {
	        caminoInverso.add(ofertas.get(actual));
	        
	        int anterior = matrizRastreo[start][actual];
	        if (anterior == -1) {
	            break; 
	        }
	        actual = anterior; 
	    }
	    
	    caminoInverso.add(ofertas.get(start));
	    
	    for (int i = caminoInverso.size() - 1; i >= 0; i--) {
	        mejoresOfertas.add(caminoInverso.get(i));
	    }
	}







}
