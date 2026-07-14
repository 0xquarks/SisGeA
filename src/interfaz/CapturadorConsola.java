package interfaz;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/*
 * Esta clase permite reutilizar los métodos ya existentes que muestran
 * información mediante System.out.println (como los reportes de
 * RankingAcademico) capturando su salida como texto, para poder
 * mostrarla dentro de un componente grafico en lugar de la consola.
 * No modifica ni duplica la lógica de esos métodos, solo redirige
 * temporalmente su salida.
 */
public class CapturadorConsola {

    /*
     * Ejecuta la acción recibida capturando todo lo que imprima por
     * System.out durante su ejecución, y devuelve ese texto.
     */
    public static String capturar(Runnable accion) {

        PrintStream salidaOriginal = System.out;

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        System.setOut(new PrintStream(buffer, true));

        try {

            accion.run();

        } finally {

            System.setOut(salidaOriginal);

        }

        return buffer.toString();
    }
}
