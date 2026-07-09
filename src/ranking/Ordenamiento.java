package ranking;

import estudiante.Estudiante;
import calificaciones.GestorCalificaciones;

/*
 * Esta clase ordena los estudiantes utilizando
 * BubbleSort y QuickSort.
 */
public class Ordenamiento {

    private int comparaciones;

    private int intercambios;

    private long tiempoMs;

    /*
     * Devuelve el número de comparaciones.
     */
    public int getComparaciones() {
        return comparaciones;
    }

    /*
     * Devuelve el número de intercambios.
     */
    public int getIntercambios() {
        return intercambios;
    }

    /*
     * Devuelve el tiempo de ejecución.
     */
    public long getTiempoMs() {
        return tiempoMs;
    }

    /*
     * Ordena utilizando BubbleSort.
     */
    public void bubbleSort(Estudiante[] arreglo, GestorCalificaciones gestor) {

        comparaciones = 0;
        intercambios = 0;

        long inicio = System.currentTimeMillis();

        for (int i = 0; i < arreglo.length - 1; i++) {

            for (int j = 0; j < arreglo.length - i - 1; j++) {

                comparaciones++;

                double promedio1 =
                        gestor.calcularPromedio(arreglo[j].getCodigo());

                double promedio2 =
                        gestor.calcularPromedio(arreglo[j + 1].getCodigo());

                if (promedio1 < promedio2) {

                    Estudiante aux = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = aux;

                    intercambios++;
                }
            }
        }

        tiempoMs = System.currentTimeMillis() - inicio;
    }

    /*
     * Ordena utilizando QuickSort.
     */
    public void quickSort(Estudiante[] arreglo,
                          GestorCalificaciones gestor,
                          int inicio,
                          int fin) {

        comparaciones = 0;
        intercambios = 0;

        long tiempoInicio = System.currentTimeMillis();

        quick(arreglo, gestor, inicio, fin);

        tiempoMs = System.currentTimeMillis() - tiempoInicio;
    }

    /*
     * Método recursivo del QuickSort.
     */
    private void quick(Estudiante[] arreglo,
                       GestorCalificaciones gestor,
                       int inicio,
                       int fin) {

        if (inicio < fin) {

            int pivote = dividir(arreglo, gestor, inicio, fin);

            quick(arreglo, gestor, inicio, pivote - 1);

            quick(arreglo, gestor, pivote + 1, fin);
        }

    }

    /*
     * Divide el arreglo alrededor del pivote.
     */
    private int dividir(Estudiante[] arreglo,
                        GestorCalificaciones gestor,
                        int inicio,
                        int fin) {

        double pivote =
                gestor.calcularPromedio(arreglo[fin].getCodigo());

        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {

            comparaciones++;

            if (gestor.calcularPromedio(arreglo[j].getCodigo()) >= pivote) {

                i++;

                Estudiante aux = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = aux;

                intercambios++;

            }

        }

        Estudiante aux = arreglo[i + 1];
        arreglo[i + 1] = arreglo[fin];
        arreglo[fin] = aux;

        intercambios++;

        return i + 1;

    }

}