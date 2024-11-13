package logica;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class GestorOfertasTest {
	
	@Test
	public void ofertaNoSolapada() {
		Oferta oferta = new Oferta("", "Alejandro", 4, 6, 1800);
		ArrayList<Oferta> ofertas = new ArrayList<>();
		
		assertTrue(GestorOfertas.esOfertaNoSolapada(oferta, ofertas)); //para probar el test pasar el metodo en Gestor OFertas a estatico
	}
	
	

}
