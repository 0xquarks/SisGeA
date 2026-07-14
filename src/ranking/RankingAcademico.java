package ranking;

import calificaciones.GestorCalificaciones;
import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import estudiante.gestion.LinkedList;
import estudiante.gestion.Nodo;

/*
 * Esta clase genera el ranking académico
 * utilizando los promedios de cada estudiante.
 */
public class RankingAcademico {

    private Gestor gestor;

    private GestorCalificaciones gestorCalificaciones;

    private Ordenamiento ordenamiento;

    private Estudiante[] ranking;

    public RankingAcademico(Gestor gestor, GestorCalificaciones gestorCalificaciones) {

        this.gestor = gestor;

        this.gestorCalificaciones = gestorCalificaciones;

        ordenamiento = new Ordenamiento();

    }

    /*
     * Genera el ranking utilizando QuickSort.
     */
    public void generarRanking() {
        LinkedList lista = gestor.getListaSecuencial();
        
        // Contar cuántos estudiantes hay en la lista
        Nodo actual = lista.getHead();
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.getSig();
        }
        
        // Crear arreglo del tamaño correcto
        ranking = new Estudiante[contador];
        
        // Llenar el arreglo
        actual = lista.getHead();
        int i = 0;
        while (actual != null && i < ranking.length) {
            ranking[i] = actual.getEstudiante();
            actual = actual.getSig();
            i++;
        }
        
        // Verificar que no haya elementos null
        if (ranking.length > 1) {
            ordenamiento.quickSort(
                    ranking,
                    gestorCalificaciones,
                    0,
                    ranking.length - 1
            );
        }
    }
    /*
     * Muestra el ranking completo.
     */
    public void mostrarRanking() {

        if (ranking == null) {

            System.out.println("Primero debe generar el ranking.");

            return;

        }

        System.out.println();

        System.out.println("RANKING GENERAL");

        for (int i = 0; i < ranking.length; i++) {

            System.out.printf(
                    "%d. %s  Promedio: %.2f%n",
                    i + 1,
                    ranking[i].getNombre(),
                    gestorCalificaciones.calcularPromedio(
                            ranking[i].getCodigo())
            );

        }

        System.out.println();

        System.out.println("Comparaciones : "
                + ordenamiento.getComparaciones());

        System.out.println("Intercambios  : "
                + ordenamiento.getIntercambios());

        System.out.println("Tiempo (ms)   : "
                + ordenamiento.getTiempoMs());

    }

    /*
     * Devuelve el mejor estudiante.
     */
    public Estudiante mejorEstudiante() {

        if (ranking == null || ranking.length == 0) {

            return null;

        }

        return ranking[0];

    }

    /*
     * Devuelve el peor estudiante.
     */
    public Estudiante peorEstudiante() {

        if (ranking == null || ranking.length == 0) {

            return null;

        }

        return ranking[ranking.length - 1];

    }

    /*
     * Compara el rendimiento de BubbleSort y QuickSort
     * ordenando una copia del ranking con cada algoritmo.
     */
    public void compararOrdenamientos() {

        if (ranking == null) {

            System.out.println("Primero debe generar el ranking.");

            return;

        }

        Estudiante[] paraBubble = ranking.clone();

        Estudiante[] paraQuick = ranking.clone();

        ordenamiento.bubbleSort(paraBubble, gestorCalificaciones);

        int comparacionesBubble = ordenamiento.getComparaciones();
        int intercambiosBubble = ordenamiento.getIntercambios();
        long tiempoBubble = ordenamiento.getTiempoMs();

        if (paraQuick.length > 1) {

            ordenamiento.quickSort(
                    paraQuick,
                    gestorCalificaciones,
                    0,
                    paraQuick.length - 1
            );

        }

        int comparacionesQuick = ordenamiento.getComparaciones();
        int intercambiosQuick = ordenamiento.getIntercambios();
        long tiempoQuick = ordenamiento.getTiempoMs();

        System.out.println();

        System.out.println("COMPARACION BUBBLESORT VS QUICKSORT");

        System.out.println();
        System.out.println("BubbleSort");
        System.out.println("  Comparaciones : " + comparacionesBubble);
        System.out.println("  Intercambios  : " + intercambiosBubble);
        System.out.println("  Tiempo (ms)   : " + tiempoBubble);

        System.out.println();
        System.out.println("QuickSort");
        System.out.println("  Comparaciones : " + comparacionesQuick);
        System.out.println("  Intercambios  : " + intercambiosQuick);
        System.out.println("  Tiempo (ms)   : " + tiempoQuick);

    }

    /*
     * Muestra el ranking de estudiantes ordenado
     * según la nota obtenida en una materia especifica.
     */
    public void mostrarRankingPorMateria(String materia) {

        if (ranking == null) {

            System.out.println("Primero debe generar el ranking.");

            return;

        }

        Estudiante[] porMateria = ranking.clone();

        for (int i = 0; i < porMateria.length - 1; i++) {

            for (int j = 0; j < porMateria.length - i - 1; j++) {

                double nota1 = gestorCalificaciones.consultarNota(
                        porMateria[j].getCodigo(), materia);

                double nota2 = gestorCalificaciones.consultarNota(
                        porMateria[j + 1].getCodigo(), materia);

                if (nota1 < nota2) {

                    Estudiante aux = porMateria[j];
                    porMateria[j] = porMateria[j + 1];
                    porMateria[j + 1] = aux;

                }

            }

        }

        System.out.println();

        System.out.println("RANKING - " + materia);

        for (int i = 0; i < porMateria.length; i++) {

            System.out.printf(
                    "%d. %s  Nota: %.2f%n",
                    i + 1,
                    porMateria[i].getNombre(),
                    gestorCalificaciones.consultarNota(
                            porMateria[i].getCodigo(), materia)
            );

        }

    }

}