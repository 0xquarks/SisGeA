package utilidades;

import estudiante.gestion.Gestor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Archivos {

    public static void loadFile(String file, Gestor gestor) {
        StringBuilder contenido = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
            return;
        }

        Pattern patron = Pattern.compile("(\\d+),([^,\\d]+),([^,\\d]+)");
        Matcher buscador = patron.matcher(contenido.toString());

        int cargados = 0;
        while (buscador.find()) {
            int codigo = Integer.parseInt(buscador.group(1).trim());
            String nombre = buscador.group(2).trim();
            String carrera = buscador.group(3).trim();
            
            gestor.registrarEstudiante(codigo, nombre, carrera);
            cargados++;
        }
        System.out.println("Estudiantes procesados: " + cargados);
    }
}