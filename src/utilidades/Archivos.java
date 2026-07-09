package utilidades;

import estudiante.gestion.Gestor;
import calificaciones.GestorCalificaciones;
import calificaciones.Materia;
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
    
    /*
     * Carga las calificaciones desde un archivo de texto.
     * Cada línea debe tener:
     * codigo,nota1,nota2,nota3
     */
    public static void cargarCalificaciones(String archivo, GestorCalificaciones gestorCalificaciones) {

        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                if (datos.length == 4) {

                    int codigo = Integer.parseInt(datos[0]);

                    double matematica = Double.parseDouble(datos[1]);

                    double programacion = Double.parseDouble(datos[2]);

                    double fisica = Double.parseDouble(datos[3]);

                    gestorCalificaciones.registrarNota(
                            codigo,
                            Materia.MATEMATICAS,
                            matematica);

                    gestorCalificaciones.registrarNota(
                            codigo,
                            Materia.PROGRAMACION,
                            programacion);

                    gestorCalificaciones.registrarNota(
                            codigo,
                            Materia.FISICA,
                            fisica);

                }

            }

            br.close();

            System.out.println("Calificaciones cargadas correctamente.");

        } catch (IOException e) {

            System.out.println("No se pudo leer el archivo de calificaciones.");

        }

    }
}