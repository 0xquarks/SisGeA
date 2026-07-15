package utilidades;

import estudiante.gestion.Gestor;
import calificaciones.GestorCalificaciones;
import calificaciones.Materia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Clase utilitaria para importar datos de estudiantes y calificaciones de manera masiva y segura desde archivos de texto. */
public class Archivos {

    public static void loadFile(String file, Gestor gestor) {
        if (file == null || gestor == null) { System.out.println("Error: Parámetros nulos en loadFile."); return; }
        Pattern patron = Pattern.compile("^(\\d+),([^,\\d]+),([^,\\d]+)$");
        int cargados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                Matcher buscador = patron.matcher(linea);
                if (buscador.matches()) {
                    try {
                        int codigo = Integer.parseInt(buscador.group(1).trim());
                        String nombre = buscador.group(2).trim();
                        String carrera = buscador.group(3).trim();
                        gestor.registrarEstudiante(codigo, nombre, carrera);
                        cargados++;
                    } catch (NumberFormatException e) {
                        System.out.println("Código inválido en línea: " + linea);
                    }
                } else {
                    System.out.println("Formato de estudiante incorrecto en línea: " + linea);
                }
            }
            System.out.println("Estudiantes cargados correctamente: " + cargados);
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de estudiantes: " + e.getMessage());
        }
    }

    public static void cargarCalificaciones(String archivo, GestorCalificaciones gestorCalificaciones) {
        if (archivo == null || gestorCalificaciones == null) { System.out.println("Error: Parámetros nulos en cargarCalificaciones."); return; }
        int cargadas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                try {
                    String[] datos = linea.split(",");
                    if (datos.length == 4) {
                        int codigo = Integer.parseInt(datos[0].trim());
                        double mat = Double.parseDouble(datos[1].trim());
                        double prog = Double.parseDouble(datos[2].trim());
                        double fis = Double.parseDouble(datos[3].trim());

                        // Validación física y lógica de rango de notas (0.0 a 10.0)
                        if (mat < 0 || mat > 10 || prog < 0 || prog > 10 || fis < 0 || fis > 10) {
                            System.out.println("Notas fuera de rango (0-10) omitidas en línea: " + linea);
                            continue;
                        }

                        gestorCalificaciones.registrarNota(codigo, Materia.MATEMATICAS, mat);
                        gestorCalificaciones.registrarNota(codigo, Materia.PROGRAMACION, prog);
                        gestorCalificaciones.registrarNota(codigo, Materia.FISICA, fis);
                        cargadas++;
                    } else {
                        System.out.println("Línea de calificación incompleta o corrupta: " + linea);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error numérico en línea: " + linea + " (" + e.getMessage() + ")");
                }
            }
            System.out.println("Calificaciones procesadas con éxito: " + cargadas);
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de calificaciones: " + e.getMessage());
        }
    }
}