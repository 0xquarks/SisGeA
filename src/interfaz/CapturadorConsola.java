package interfaz;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/*
 * Clase utilitaria para redirigir temporalmente la salida de consola (System.out)
 * y capturarla como texto sin perder caracteres especiales ni romper el flujo estándar.
 */
public class CapturadorConsola {

    /*
     * Ejecuta la acción de forma segura redirigiendo System.out, garantizando
     * la restauración del flujo original y soportando codificación UTF-8.
     */
    public static String capturar(Runnable accion) {
        if (accion == null) {
            return "Error: Acción de captura no especificada.";
        }

        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // Se define UTF-8 explícitamente para evitar problemas de codificación con acentos y la 'ñ'
            PrintStream nuevoOut = new PrintStream(buffer, true, StandardCharsets.UTF_8.name());
            System.setOut(nuevoOut);

            accion.run();

            nuevoOut.flush();
            return buffer.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return "Excepción capturada durante la ejecución: " + e.getMessage();
        } finally {
            // Se garantiza la restauración de la consola original bajo cualquier circunstancia
            System.setOut(salidaOriginal);
        }
    }
}