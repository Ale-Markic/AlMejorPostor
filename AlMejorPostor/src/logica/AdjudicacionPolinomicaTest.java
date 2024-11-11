package logica;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class AdjudicacionPolinomicaTest {
	private Date fecha = new Date();

	@Test
	public void noOfertasDevuelveError() {
		ArrayList<Oferta> ofertas = new ArrayList<>();
		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertTrue(resultado.isEmpty(), "Si no hay ofertas, el resultado debería estar vacío");
	}

	@Test
	public void testFloydWarshall_OfertasSinSuperposicion() {
		ArrayList<Oferta> ofertas = new ArrayList<>();

		Oferta oferta1 = new Oferta(fecha,"Lebron",1,5,100);
		Oferta oferta2 = new Oferta(fecha,"James",6,10,200);

		ofertas.add(oferta1);
		ofertas.add(oferta2);

		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertEquals(2, resultado.size(), "Debería seleccionar ambas ofertas al no superponerse");
	}

	@Test
	public void testFloydWarshall_OfertasConSuperposicion() {
		ArrayList<Oferta> ofertas = new ArrayList<>();

		Oferta oferta1 = new Oferta(fecha, "Stephen",1,5,100);
		Oferta oferta2 = new Oferta(fecha, "Curry",4,10,200);

		ofertas.add(oferta1);
		ofertas.add(oferta2);

		ArrayList<Oferta> resultado = AdjudicacionPolinomial.obtenerMejoresOfertas(ofertas);
		assertTrue(resultado.size() == 1, "Debería seleccionar solo una oferta al haber superposición");
	}

	/*
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
	*/
	@Test
	public void testInicializarMatrices() {
	    // Crear algunas ofertas para probar
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta(fecha, "Max", 10, 12, 100)); 
		ofertas.add(new Oferta(fecha, "Verstappen",12, 14, 120));
		ofertas.add(new Oferta(fecha, "Kobe",14, 16, 80));

	    // Inicializar matrices
	    int n = ofertas.size();
	    int[][] matrizGanancia = new int[n][n];
	    int[][] matrizRastreo = new int[n][n];

	    AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);

	    // Verificar las condiciones de la matriz de ganancia y de rastreo

	    // Caso de i == j (cada oferta consigo misma)
	    assertEquals(100, matrizGanancia[0][0]);
	    assertEquals(-1, matrizRastreo[0][0]);

	    assertEquals(150, matrizGanancia[1][1]);
	    assertEquals(-1, matrizRastreo[1][1]);

	    assertEquals(200, matrizGanancia[2][2]);
	    assertEquals(-1, matrizRastreo[2][2]);

	    assertEquals(50, matrizGanancia[3][3]);
	    assertEquals(-1, matrizRastreo[3][3]);

	    // Caso de ofertas no superpuestas (horarios consecutivos)
	    assertEquals(250, matrizGanancia[0][1]);  // Oferta 1 -> Oferta 2: 100 + 150
	    assertEquals(0, matrizGanancia[1][3]);    // Oferta 2 -> Oferta 4: deben superponerse, ganancia 0
	    assertEquals(0, matrizGanancia[3][2]);    // Oferta 4 -> Oferta 3: deben superponerse, ganancia 0
	    assertEquals(350, matrizGanancia[0][2]);  // Oferta 1 -> Oferta 3: 100 + 200

	    // Verificar la matriz de rastreo para estos casos
	    assertEquals(0, matrizRastreo[0][1]);  // Oferta 1 -> Oferta 2
	    assertEquals(-1, matrizRastreo[1][3]); // Oferta 2 -> Oferta 4 (se superponen)
	    assertEquals(-1, matrizRastreo[3][2]); // Oferta 4 -> Oferta 3 (se superponen)
	    assertEquals(0, matrizRastreo[0][2]);  // Oferta 1 -> Oferta 3
	}
	
	@Test
	public void testAplicarFloydWarshall() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta(fecha, "Novak", 1, 3, 10)); 
	    ofertas.add(new Oferta(fecha, "Rafa", 4, 6, 20)); 
	    ofertas.add(new Oferta(fecha, "Roger", 5, 7, 15));  


	    int[][] matrizGanancia = new int[3][3];
	    int[][] matrizRastreo = new int[3][3];
	    
	    AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);
	    AdjudicacionPolinomial.aplicarFloydWarshall(ofertas, matrizGanancia, matrizRastreo);
	    
	    
	    
	    assertEquals(25, matrizGanancia[0][2]);  
	    assertEquals(30, matrizGanancia[0][1]); 
	    assertEquals(0, matrizGanancia[1][2]);   
	}
	
	@Test
	public void testEncontrarMaxGanancia() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta(fecha, "Novak", 10, 12, 100)); 
	    ofertas.add(new Oferta(fecha, "Rafa",12, 14, 120));
	    ofertas.add(new Oferta(fecha, "Roger",14, 16, 80));

	    int[][] matrizGanancia = new int[3][3];
	    int[][] matrizRastreo = new int[3][3];
	    
	    AdjudicacionPolinomial.inicializarMatrices(ofertas, matrizGanancia, matrizRastreo);
	    AdjudicacionPolinomial.aplicarFloydWarshall(ofertas, matrizGanancia, matrizRastreo);

	    
	    int[] resultado = AdjudicacionPolinomial.encontrarMaxGanancia(matrizGanancia);	    

	    assertEquals(0, resultado[0]);
	    assertEquals(1, resultado[1]);
	    assertEquals(220, resultado[2]);
	}
	
	
	@Test
	public void testReconstruirCamino() {
	    ArrayList<Oferta> ofertas = new ArrayList<>();
	    ofertas.add(new Oferta(fecha, "Alejandro", 10, 12, 100)); 
	    ofertas.add(new Oferta(fecha, "Gaston", 12, 14, 120));
	    ofertas.add(new Oferta(fecha, "Esteban", 14, 16, 80));
	    
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
