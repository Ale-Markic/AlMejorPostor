package gestionDeArchivos;

import logica.Oferta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorDeArchivos {
	private static final String ARCHIVO_JSON = "ofertas.json";
	private Gson gson;


	public GestorDeArchivos() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	

	/*
	 *  Método para crear o inicializar el archivo JSON vacío si no existe
	 */
	public void inicializarArchivo() {
		File archivo = new File(ARCHIVO_JSON);
		if (!archivo.exists()) {
			try (FileWriter writer = new FileWriter(archivo)) {
				writer.write("[]");  
				System.out.println("Archivo JSON creado.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * guarda la nueva oferta en el archivo JSON
	 * @param nuevaOferta
	 */
	public void agregarOferta(Oferta nuevaOferta) {
		ArrayList<Oferta> ofertas = cargarOfertas();
		ofertas.add(nuevaOferta);

		try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
			gson.toJson(ofertas, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga las ofertas al iniciar la aplicacion
	 */
	public ArrayList <Oferta> cargarOfertas() {
		try (Reader reader = new FileReader(ARCHIVO_JSON)) {
			Type tipoListaOfertas = new TypeToken<List<Oferta>>() {}.getType();
			ArrayList<Oferta> ofertas = gson.fromJson(reader, tipoListaOfertas);

			if (ofertas == null) {
				return new ArrayList<>();
			}
			return ofertas;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que muestra las ofertas por consola pero que deberia hacerlo por pantalla.
	 */
	public void mostrarOfertas() {
		List<Oferta> ofertas = cargarOfertas();

		if(ofertas.isEmpty()) {
			
		}else {
			String jsonFormateado = gson.toJson(ofertas);
			System.out.println(jsonFormateado);
		}
	}
	
	/**
	 * Metodo para eliminar las ofertas de una persona.
	 *
	 * 
	 * ES IMPORTANTE REMARCAR QUE SE ELIMINAN TODAS LAS OFERTAS QUE ESTAN ASOCIADAS AL NOMBRE QUE SE PASA POR PARAMETRO
	 * @param nombreOferente
	 */
	public void eliminarOferta(String nombreOferente) {
		List<Oferta> ofertas = cargarOfertas();
		
		ofertas.removeIf(oferta -> oferta.getNombreOferente().equals(nombreOferente));
		
	    try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
	        gson.toJson(ofertas, writer);
	        System.out.println("Oferta eliminada exitosamente.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}
	
	public void borrarTodo() {
		try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
	        writer.write("[]");  
	        System.out.println("Todos los datos han sido borrados del archivo JSON.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
