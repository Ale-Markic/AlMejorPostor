package gestionDeArchivos;

import logica.Oferta;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorDeArchivos {
	private static final String ARCHIVO_JSON = "ofertas.json";
	private Gson gson;
	
	
    public GestorDeArchivos() {
        this.gson = new Gson();
    }
    
    /*
     *  Método para crear o inicializar el archivo JSON vacío si no existe
     */
    public void inicializarArchivo() {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            try (FileWriter writer = new FileWriter(archivo)) {
                writer.write("[]");  // Crear archivo con un array JSON vacío
                System.out.println("Archivo JSON creado.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void guardarOferta(Oferta oferta) {
		
	}
	
	/**
	 * Carga las ofertas al iniciar la aplicacion
	 */
	public void cargarOferta() {
		
	}

}
