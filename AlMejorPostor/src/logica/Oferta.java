package logica;

import java.math.BigDecimal;

public class Oferta {
	String nombreOferente;
	int horaInicio, horaFin;
	BigDecimal monto;
	
	
	public Oferta(String nombreOferente, int horaInicio, int horaFin, int monto) {
		this.monto = convertirIntABigDecimal(monto);
		this.nombreOferente = nombreOferente;
		this.horaFin = horaFin;
		this.horaInicio = horaInicio;
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
		
		return "";
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
