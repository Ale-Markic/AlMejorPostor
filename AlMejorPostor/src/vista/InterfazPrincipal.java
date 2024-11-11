package vista;

import logica.Oferta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import controlador.ControladorSalaEnsayo;
import gestionDeArchivos.GestorDeArchivos;

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
	//    private ControladorSalaEnsayo controlador;  // Controlador declarado aquí

	// Método main agregado nuevamente
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				InterfazPrincipal window = new InterfazPrincipal(); // Inicializamos sin parámetros
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public InterfazPrincipal() {
		initialize(); // Inicializa la vista
		GestorDeArchivos gestorDeArchivos = new GestorDeArchivos(); // Crear el gestor de archivos aquí

		// Inicializar el controlador ANTES de cualquier otra llamada que lo utilice
		//controlador = new InterfazPrincipal(this, gestorDeArchivos); // Inicializa el controlador con el gestor

		configurarListeners(); // Configura los listeners después de que todo esté inicializado
		actualizarTablaConOfertas(calendar.getDate());  // Carga las ofertas del día actual aquí, después de inicializar el controlador
	}

	private void initialize() {
		inicializarFrame();
		
		
		JPanel panelFormulario = new JPanel(new GridLayout(5, 2));
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

		btnAgregar = new JButton("Agregar Oferta");
		panelFormulario.add(btnAgregar);

		JPanel ofertasPanel = new JPanel(new BorderLayout());
		String[] columnas = {"Nombre", "Hora Inicio", "Hora Fin", "Monto"};
		modeloTabla = new DefaultTableModel(columnas, 0);
		tablaOfertas = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tablaOfertas);
		ofertasPanel.add(scrollPane, BorderLayout.CENTER);

		calendar = new JCalendar();
		ofertasPanel.add(calendar, BorderLayout.WEST);

		// Listener para el calendario
		calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
			Date selectedDate = calendar.getDate();
			actualizarTablaConOfertas(selectedDate);  // Esto solo funciona si el controlador ya está inicializado
		});

		btnAgregar.addActionListener(e -> agregarOferta());

		// Botón "Mostrar Ofertas"
		btnMostrarOfertas = new JButton("Mostrar Ofertas");
		panelFormulario.add(btnMostrarOfertas);

		// Botón "Resolver Adjudicación"
		btnResolverAdjudicacion = new JButton("Resolver Adjudicación");
		panelFormulario.add(btnResolverAdjudicacion);

		frame.getContentPane().add(ofertasPanel, BorderLayout.CENTER);
	}

	private void agregarOferta() {
		try {
			String nombre = txtNombre.getText();
			int horaInicio = Integer.parseInt(txtHoraInicio.getText());
			int horaFin = Integer.parseInt(txtHoraFin.getText());
			double monto = Double.parseDouble(txtMonto.getText());

			//controlador.agregarOferta(nombre, horaInicio, horaFin, monto); 
			JOptionPane.showMessageDialog(frame, "Oferta agregada exitosamente");
			limpiarFormulario();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Por favor, ingrese valores válidos para las horas y el monto.", "Error de formato", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTablaConOfertas(Date fecha) {
		modeloTabla.setRowCount(0);  // Limpiar la tabla
		ArrayList<Oferta> ofertas = controlador.obtenerOfertasPorFecha(fecha);
		for (Oferta oferta : ofertas) {
			Object[] fila = {
					oferta.getNombre(),
					oferta.getHoraInicio(),
					oferta.getHoraFin(),
					oferta.getMonto()
			};
			modeloTabla.addRow(fila);
		}
	}

	public String getNombre() {
		return txtNombre.getText();
	}

	public String getHoraInicio() {
		return txtHoraInicio.getText();
	}

	public String getHoraFin() {
		return txtHoraFin.getText();
	}

	public String getMonto() {
		return txtMonto.getText();
	}

	public Date getFechaSeleccionada() {
		return calendar.getDate();
	}

	// Métodos para añadir listeners
	public void addAgregarOfertaListener(ActionListener listener) {
		btnAgregar.addActionListener(listener);
	}

	public void addMostrarOfertasListener(ActionListener listener) {
		btnMostrarOfertas.addActionListener(listener);
	}

	public void addResolverAdjudicacionListener(ActionListener listener) {
		btnResolverAdjudicacion.addActionListener(listener);
	}

	// Método para configurar los listeners
	private void configurarListeners() {
		btnAgregar.addActionListener(e -> agregarOferta());
		// Agregar más listeners si es necesario
	}

	// Método para actualizar la tabla con las ofertas filtradas
	public void actualizarTabla(ArrayList<Oferta> ofertas) {
		modeloTabla.setRowCount(0); // Limpiar la tabla

		for (Oferta oferta : ofertas) {
			Object[] fila = {
					oferta.getNombre(),
					oferta.getHoraInicio(),
					oferta.getHoraFin(),
					oferta.getMonto()
			};
			modeloTabla.addRow(fila);
		}
	}

	// Método para mostrar un mensaje de información o error
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	// Método para limpiar el formulario de oferta
	public void limpiarFormulario() {
		txtNombre.setText("");
		txtHoraInicio.setText("");
		txtHoraFin.setText("");
		txtMonto.setText("");
	}
	
	private void inicializarFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

	}
}