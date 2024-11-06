package logica;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;

import java.util.ArrayList;

public class AdjudicacionPolinomicaTest {

	@Test
	public void noOfertasDevuelveError() {
		ArrayList<Oferta> ofertas = new ArrayList<>();
		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertTrue(resultado.isEmpty(), "Si no hay ofertas, el resultado debería estar vacío");
	}

	@Test
	public void testFloydWarshall_OfertasSinSuperposicion() {
		ArrayList<Oferta> ofertas = new ArrayList<>();

		Oferta oferta1 = new Oferta("Lebron",1,5,100);
		Oferta oferta2 = new Oferta("James",6,10,200);

		ofertas.add(oferta1);
		ofertas.add(oferta2);

		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertEquals(2, resultado.size(), "Debería seleccionar ambas ofertas al no superponerse");
	}

	@Test
	public void testFloydWarshall_OfertasConSuperposicion() {
		ArrayList<Oferta> ofertas = new ArrayList<>();

		Oferta oferta1 = new Oferta("Stephen",1,5,100);
		Oferta oferta2 = new Oferta("Curry",4,10,200);

		ofertas.add(oferta1);
		ofertas.add(oferta2);

		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertTrue(resultado.size() == 1, "Debería seleccionar solo una oferta al haber superposición");
	}

	@Test
	public void testInicializarMatrices() {
		ArrayList<Oferta> ofertas = new ArrayList<>();
		ofertas.add(new Oferta("Max", 10, 12, 100)); 
		ofertas.add(new Oferta("Verstappen",12, 14, 120));
		ofertas.add(new Oferta("Kobe",14, 16, 80));

		int[][] matrizGanancia = new int[3][3];
		int[][] matrizRastreo = new int[3][3];

		AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);

		assertEquals(100, matrizGanancia[0][0]);
		assertEquals(120, matrizGanancia[0][1]);
		assertEquals(80, matrizGanancia[1][2]);

		assertEquals(-1, matrizRastreo[0][0]);
		assertEquals(0, matrizRastreo[0][1]);
		assertEquals(1, matrizRastreo[1][2]);
	}
	
	@Test
	public void testAplicarFloydWarshall() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta("Novak", 8, 10, 100)); // Oferta 1
	    ofertas.add(new Oferta("Rafa", 12, 14, 120)); // Oferta 2
	    ofertas.add(new Oferta("Roger", 14, 16, 80));  // Oferta 3

	    // Inicializar matrices
	    int[][] matrizGanancia = new int[3][3];
	    int[][] matrizRastreo = new int[3][3];
	    
	    AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);
	    AdjudicacionPolinomial.aplicarFloydWarshall(ofertas, matrizGanancia, matrizRastreo);
	    
	    // Verificar la matriz de ganancia después de aplicar Floyd-Warshall
	    assertEquals(200, matrizGanancia[0][2]);  // Comprobando la ganancia total entre la oferta 1 y la 3
	    assertEquals(120, matrizGanancia[0][1]);  // Ganancia entre la oferta 1 y la 2
	    assertEquals(80, matrizGanancia[1][2]);   // Ganancia entre la oferta 2 y la 3
	}
	
	@Test
	public void testEncontrarMaxGanancia() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta("Novak", 10, 12, 100)); 
	    ofertas.add(new Oferta("Rafa",12, 14, 120));
	    ofertas.add(new Oferta("Roger",14, 16, 80));

	    int[][] matrizGanancia = new int[3][3];
	    int[][] matrizRastreo = new int[3][3];
	    
	    AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);
	    AdjudicacionPolinomial.aplicarFloydWarshall(ofertas, matrizGanancia, matrizRastreo);

	    
	    int[] resultado = AdjudicacionPolinomial.encontrarMaxGanancia(matrizGanancia);
	    
	    // Verificar que la ganancia máxima se haya encontrado correctamente
	    assertEquals(0, resultado[0]);
	    assertEquals(1, resultado[1]);
	    assertEquals(220, resultado[2]);
	}
	
	
	@Test
	public void testReconstruirCamino() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta("Alejandro", 10, 12, 100)); 
	    ofertas.add(new Oferta("Gaston", 12, 14, 120));
	    ofertas.add(new Oferta("Esteban", 14, 16, 80));
	    
	    int[][] matrizRastreo = {
	        {-1, 0, 1},
	        {-1, -1, 1},
	        {-1, -1, -1}
	    };
	    
	    ArrayList<Oferta> mejoresOfertas = new ArrayList<>();
	    AdjudicacionPolinomial.reconstruirCamino(matrizRastreo, ofertas, mejoresOfertas, 0, 2);
	    
	    // Verificar que el camino reconstruido sea el esperado
	    assertEquals(2, mejoresOfertas.size());
	    assertTrue(mejoresOfertas.contains(ofertas.get(0)));
	    assertTrue(mejoresOfertas.contains(ofertas.get(1)));
	}



}
