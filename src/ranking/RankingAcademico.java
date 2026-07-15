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
     * Ejecuta BubbleSort y QuickSort sobre los estudiantes y muestra
     * cuantas comparaciones, intercambios y tiempo tomo cada uno.
     */
    public void compararOrdenamientos() {
        LinkedList lista = gestor.getListaSecuencial();
        Nodo actual = lista.getHead();
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.getSig();
        }

        if (contador < 2) {
            System.out.println("Se necesitan al menos 2 estudiantes.");
            return;
        }

        // Dos copias para que cada algoritmo trabaje sobre datos frescos
        Estudiante[] copia1 = new Estudiante[contador];
        Estudiante[] copia2 = new Estudiante[contador];
        actual = lista.getHead();
        int i = 0;
        while (actual != null) {
            copia1[i] = actual.getEstudiante();
            copia2[i] = actual.getEstudiante();
            actual = actual.getSig();
            i++;
        }

        ordenamiento.bubbleSort(copia1, gestorCalificaciones);
        System.out.println("BubbleSort:");
        System.out.println("  Comparaciones: " + ordenamiento.getComparaciones());
        System.out.println("  Intercambios : " + ordenamiento.getIntercambios());
        System.out.println("  Tiempo (ms)  : " + ordenamiento.getTiempoMs());

        ordenamiento.quickSort(copia2, gestorCalificaciones, 0, copia2.length - 1);
        System.out.println("QuickSort:");
        System.out.println("  Comparaciones: " + ordenamiento.getComparaciones());
        System.out.println("  Intercambios : " + ordenamiento.getIntercambios());
        System.out.println("  Tiempo (ms)  : " + ordenamiento.getTiempoMs());
    }

    /*
     * Muestra los estudiantes ordenados de mayor a menor nota
     * en una materia especifica.
     */
    public void mostrarRankingPorMateria(String materia) {
        LinkedList lista = gestor.getListaSecuencial();
        Nodo actual = lista.getHead();
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.getSig();
        }

        if (contador == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        // Copiar estudiantes a un arreglo
        Estudiante[] arreglo = new Estudiante[contador];
        actual = lista.getHead();
        int i = 0;
        while (actual != null) {
            arreglo[i] = actual.getEstudiante();
            actual = actual.getSig();
            i++;
        }

        // Ordenar de mayor a menor nota con BubbleSort
        for (int x = 0; x < arreglo.length - 1; x++) {
            for (int j = 0; j < arreglo.length - x - 1; j++) {
                double nota1 = gestorCalificaciones.consultarNota(arreglo[j].getCodigo(), materia);
                double nota2 = gestorCalificaciones.consultarNota(arreglo[j + 1].getCodigo(), materia);
                if (nota1 < nota2) {
                    Estudiante temp = arreglo[j];
                    arreglo[j]     = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
            }
        }

        System.out.println("Ranking por " + materia + ":");
        for (int k = 0; k < arreglo.length; k++) {
            double nota = gestorCalificaciones.consultarNota(arreglo[k].getCodigo(), materia);
            System.out.println((k + 1) + ". " + arreglo[k].getNombre() + " - Nota: " + nota);
        }
    }}