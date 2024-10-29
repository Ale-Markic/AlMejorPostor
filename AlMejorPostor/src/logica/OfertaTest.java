package logica;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class OfertaTest {
	
	@Test
	public void happyPathOfertaNoArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, 10, 15000);
		
		assertNotNull("la oferta se creó exitosamente", oferta);
	}
	
	@Test(expected = RuntimeException.class)
	public void nombreVacioArrojaError() {
		Oferta oferta = new Oferta("", 4, 7, 10000);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void ingresarMontoNegativoArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, 10, -15000);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarMontoIgualACeroArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, 10, 0);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarHoraInicioNegativaArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , -7, 10, 15000);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarHoraFinNegativaArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, -10, 15000);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarHoraInicioAltaArrojaError() {
		Oferta oferta = new Oferta("Alejandro" ,25, 10, 15000);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarHoraFinAltaArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, 25, 15000);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresarHorasIgualesArrojaError() {
		Oferta oferta = new Oferta("Alejandro" , 7, 7, 15000);
	}
	
	@Test
	public void ingresoHoraInicioLimiteBajo() {
		Oferta oferta = new Oferta("Alejandro" , 0, 7, 15000);
		
		assertNotNull("la oferta se creó exitosamente", oferta);
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresoHoraFinLimiteBajo() {
		//Este test claramente es de redundancia, hay muchas barreras que deberia detectar este error. Pero igual del usuario lo puede ingresar
		Oferta oferta = new Oferta("Alejandro" , 0, 0, 15000);
		
	}
	
	@Test(expected = RuntimeException.class)
	public void ingresoHoraInicioLimiteAlto() {
		//Este test claramente es de redundancia, hay muchas barreras que deberia detectar este error. Pero igual el usuario lo puede ingresar
		Oferta oferta = new Oferta("Alejandro" , 24, 24, 15000);
	}
	
	@Test
	public void ingresoHoraFinLimiteAlto() {
		Oferta oferta = new Oferta("Alejandro", 3, 24, 15000);
		assertNotNull("la oferta se creó exitosamente", oferta);
		
	}
	

}
