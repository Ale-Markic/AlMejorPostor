package logica;

import java.math.BigDecimal;

public class Oferta {
	String nombreOferente;
	int horaInicio, horaFin;
	BigDecimal monto;
	
	
	public Oferta(String nombreOferente, int horaInicio, int horaFin, int monto) {
		chequearLegalidadDeOferta(horaInicio, horaFin, monto);
		this.monto = convertirIntABigDecimal(monto);
		this.nombreOferente = nombreOferente;
		this.horaFin = horaFin;
		this.horaInicio = horaInicio;
	}
	
	private void chequearLegalidadDeOferta(int horaInicio, int horaFin, int monto) {
		if(horaInicio > horaFin) {
			throw new RuntimeException("No puede viajar al pasado, la hora de fin tiene que ser posterior a la hora de comienzo");
		}
		if(horaInicio < 0 || horaFin < 0) {
			throw new RuntimeException(" no se puede ingresar horas negativas");
		}
		if(horaInicio > 24 || horaFin > 24) {
			throw new RuntimeException("el día solo tiene 24 horas");
		}
		if(monto < 0) {
			throw new RuntimeException("por favor ingrese un monto con valor positivo");
		}
	}
	
	
	
	
	private BigDecimal convertirIntABigDecimal(int monto) {
		return BigDecimal.valueOf(monto);
	}
	
	@Override
	public String toString() {
		StringBuilder mensaje = new StringBuilder();
		
		mensaje.append("Nombre del ofertante: " + getNombreOferente());
		mensaje.append("Hora de inicio: " + getHoraInicio());
		mensaje.append("Hora de fin: " + getHoraFin());
		mensaje.append("Monto ofertado: " + getMonto());
		
		return mensaje.toString();
	}

	public String getNombreOferente() {
		return nombreOferente;
	}

	public void setNombreOferente(String nombreOferente) {
		this.nombreOferente = nombreOferente;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
}
