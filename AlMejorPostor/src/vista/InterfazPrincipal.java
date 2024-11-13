package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

public class InterfazPrincipal {
	private JFrame frame;
	private JCalendar calendar;
	private JTextField txtNombre;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JTextField txtMonto;
	private JTable tablaOfertas;
	private DefaultTableModel modeloTabla;
	private JButton btnAgregar;
	private JButton btnMostrarOfertas;
	private JButton btnResolverAdjudicacion;
	private String fechaSeleccionada;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				InterfazPrincipal window = new InterfazPrincipal();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public InterfazPrincipal() {
		initialize();
	}

	private void initialize() {
		inicializarFrame();
		mostrarMensajeBienvenida();


		JPanel panelFormulario = new JPanel(new GridLayout(0, 2));
		configurarPanelFormulario(panelFormulario);


		JPanel ofertasPanel = new JPanel(new BorderLayout());
		configurarOfertasPanel(ofertasPanel);


		calendar = new JCalendar();
		ofertasPanel.add(calendar, BorderLayout.WEST);

	
		calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
			Date selectedDate = calendar.getDate();
		});
		
		btnAgregar = new JButton("Agregar Oferta");
		panelFormulario.add(btnAgregar);
		
		btnMostrarOfertas = new JButton("Mostrar Ofertas");
		panelFormulario.add(btnMostrarOfertas);

		btnResolverAdjudicacion = new JButton("Resolver Adjudicación");
		panelFormulario.add(btnResolverAdjudicacion);

		frame.getContentPane().add(ofertasPanel, BorderLayout.CENTER);

		capturarFecha();
	}

	public void capturarFecha() {
		SimpleDateFormat particion = new SimpleDateFormat("DD/MM/YYYY");
		String fechasParticionada = particion.format(calendar.getDate());
		this.fechaSeleccionada=  fechasParticionada;
	}
	
	public void accionFechaSeleccionada(PropertyChangeListener listener) {
		calendar.getDayChooser().addPropertyChangeListener("day", listener);
	}

	public String ObtenerNombre() {
		return txtNombre.getText();
	}

	public String ObtenerHoraInicio() {
		return txtHoraInicio.getText();
	}

	public String ObtenerHoraFin() {
		return txtHoraFin.getText();
	}

	public String ObtenerMonto() {
		return txtMonto.getText();
	}

	public String ObtenerFechaSeleccionada() {
		return  this.fechaSeleccionada;
	}

	public void MostrarFechaSeleccionada(PropertyChangeListener  evento) {
		calendar.getDayChooser().addPropertyChangeListener("day", evento);
	}


	public void addAgregarOfertaListener(ActionListener listener) {
		this.btnAgregar.addActionListener(listener);
	}

	public void addMostrarOfertasListener(ActionListener listener) {
		this.btnMostrarOfertas.addActionListener(listener);
	}

	public void addResolverAdjudicacionListener(ActionListener listener) {
		limPiarTabla();
		this.btnResolverAdjudicacion.addActionListener(listener);
	}

	public void limPiarTabla() { 
		modeloTabla.setRowCount(0);
	}

	public void RecargarTabla() {
		this.frame.revalidate();
	}

	public void IngresarOfertasEnTabla( String nombreOferente, int horaInicio, int horaFin, int monto){

		String [] datos = new String[4];
		datos[0]=nombreOferente;
		datos[1]= String.valueOf(horaInicio);
		datos[2]= String.valueOf(horaFin);
		datos[3]=String.valueOf(monto);

		modeloTabla.addRow(datos);
		RecargarTabla();
	}

	public void mostrarPantalla(){
		EventQueue.invokeLater(() -> {
			frame.setVisible(true);
		});    
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	public void limpiarFormulario() {
		txtNombre.setText("");
		txtHoraInicio.setText("");
		txtHoraFin.setText("");
		txtMonto.setText("");
	}
	
	private void mostrarMensajeBienvenida() {
		String mensaje = "BIENVENIDO! \nAntes de empezar por favor tener en cuenta las siguientes consideraciones: \n"
				+ "Funcionamiento: \nPara cargar una oferta debe seleccionar la fecha que desee en el calendario, cargar los datos necesarios y luego presionar el boton 'agregar oferta' \n y el sistema solo se encargará de "
				+ "adjudicar la oferta a ese día. \nLuego, si usted quiere visualizar las ofertas existentes de un dia en especifico, lo unico que "
				+ "debe hacer es hacer click en el dia que quiera y luego apretar el boton de mostrarOferta, \nel sistema solo mostrará las ofertas de ese día. \n"
				+ "Por ultimo, si usted quiere ver la mejor adjudicacion de oferta de ese día en especifico, simplemente debe hacer click en el calendario sobre el dia \n"
				+ "que quiera saber, y el sistema solo filtrará y obtentrá la mejor adjudicacion";
		//En realidad, no podemos garantizar que es la mejor solucion, ya que esta se obtiene de manera golosa, con una heuristica. Pero el usuario no tiene porque saber eso.
		mostrarMensaje(mensaje);
	}

	private void inicializarFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
	}

	private void configurarPanelFormulario(JPanel panelFormulario) {
		frame.getContentPane().add(panelFormulario, BorderLayout.NORTH);

		panelFormulario.add(new JLabel("Nombre:"));
		txtNombre = new JTextField();
		panelFormulario.add(txtNombre);
		panelFormulario.add(new JLabel("Hora Inicio:"));
		txtHoraInicio = new JTextField();
		panelFormulario.add(txtHoraInicio);

		panelFormulario.add(new JLabel("Hora Fin:"));
		txtHoraFin = new JTextField();
		panelFormulario.add(txtHoraFin);

		panelFormulario.add(new JLabel("Monto:"));
		txtMonto = new JTextField();
		panelFormulario.add(txtMonto);
	}

	private void configurarOfertasPanel(JPanel ofertasPanel) {
		String[] columnas = {"Nombre", "Hora Inicio", "Hora Fin", "Monto"};
		modeloTabla = new DefaultTableModel(columnas, 0);
		tablaOfertas = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tablaOfertas);
		ofertasPanel.add(scrollPane, BorderLayout.CENTER);
	}
}