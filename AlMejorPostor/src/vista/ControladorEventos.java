package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import logica.GestorOfertas;
import logica.Oferta;

public class ControladorEventos {
	private InterfazPrincipal vista;
	private GestorOfertas gestorOferta;

	
	public ControladorEventos(InterfazPrincipal vista,GestorOfertas gestorOferta) {
		this.vista = vista;
		this.gestorOferta = gestorOferta;

		this.vista.addAgregarOfertaListener(new AgregarOfertaListener());
		this.vista.addMostrarOfertasListener(new MostrarOfertasListener());
		this.vista.addResolverAdjudicacionListener(new ResolverAdjudicacionListener());
		this.vista.MostrarFechaSeleccionada(new FechaSeleccionada());
	}

	class AgregarOfertaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Oferta ofertaObtenida= new Oferta(vista.ObtenerFechaSeleccionada(),vista.ObtenerNombre(),Integer.valueOf(vista.ObtenerHoraInicio()),Integer.valueOf(vista.ObtenerHoraFin()),Integer.valueOf(vista.ObtenerMonto()));

			if(gestorOferta.esNuevaOferta(ofertaObtenida)) {

				gestorOferta.agregarOferta(ofertaObtenida);

				AgregarListadoDeOfertasEnVista(gestorOferta.obtenerOfertas());
				vista.mostrarMensaje("Oferta cargada exitosamente");
			}
		}
	}

	class MostrarOfertasListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AgregarListadoDeOfertasEnVista(gestorOferta.obtenerOfertasDeEstaFecha(vista.ObtenerFechaSeleccionada()));

		}
	}

	class ResolverAdjudicacionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			AgregarListadoDeOfertasEnVista(gestorOferta.adjudicacionGolosa(vista.ObtenerFechaSeleccionada()));
			vista.RecargarTabla();
		}
	}


	class FechaSeleccionada implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			vista.capturarFecha();
		}
	}

	private void AgregarListadoDeOfertasEnVista(ArrayList<Oferta> listadoOFerta) {
		vista.limPiarTabla();

		for (Oferta ofertas : listadoOFerta) {
			vista.IngresarOfertasEnTabla( ofertas.getNombreOferente(), ofertas.getHoraInicio(), ofertas.getHoraFin(),ofertas.getMonto());
		}
	}
}
