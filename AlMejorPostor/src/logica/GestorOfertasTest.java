package logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class GestorOfertasTest {
	private GestorOfertas gestorOfertas = new GestorOfertas();
	
	@Test
	public void ofertaNoSolapada() {
		Oferta oferta = new Oferta("1900-1-1", "Alejandro", 4, 6, 1800);
		ArrayList<Oferta> ofertas = new ArrayList<>();
		
		assertTrue(gestorOfertas.esOfertaNoSolapada(oferta, ofertas)); 
	}
	
    @Test
    public void testAdjudicacionGolosaSinSolapamiento() {
        Oferta oferta1 = new Oferta("1900-1-1","oferta1", 10, 12, 50);
        Oferta oferta2 = new Oferta("1900-1-1","oferta2", 12, 14, 40);
        
        gestorOfertas.agregarOferta(oferta1);
        gestorOfertas.agregarOferta(oferta2);

        ArrayList<Oferta> seleccionadas = gestorOfertas.adjudicacionGolosa("1900-1-1");
        
        assertEquals(2, seleccionadas.size());
    }
    
    @Test
    public void testEsOfertaNoSolapada() {
        ArrayList<Oferta> ofertasSeleccionadas = new ArrayList<>();
        
        Oferta ofertaExistente = new Oferta("1900-1-1", "OfertaExistente", 10, 12, 50);
        ofertasSeleccionadas.add(ofertaExistente);
        
        Oferta nuevaOferta = new Oferta("1900-1-1","nuevaOferta", 12, 14, 40);

        assertTrue(gestorOfertas.esOfertaNoSolapada(nuevaOferta, ofertasSeleccionadas));
    }
}
