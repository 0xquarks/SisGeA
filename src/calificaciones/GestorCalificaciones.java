package calificaciones;

import estudiante.gestion.Gestor;
import estudiante.gestion.LinkedList;
import estudiante.gestion.Nodo;

/*
 * Esta clase administra las calificaciones de los estudiantes.
 * Guarda las notas en una matriz donde cada fila representa un estudiante
 * y cada columna una materia.
 */
public class GestorCalificaciones {

    // Permite acceder a los estudiantes registrados
    private Gestor gestor;

    // Guarda los códigos de los estudiantes
    private int[] codigos;

    // Matriz de notas
    private double[][] notas;

    // Cantidad de estudiantes registrados
    private int cantidad;

    /*
     * Inicializa la matriz de notas.
     */
    public GestorCalificaciones(Gestor gestor) {

        this.gestor = gestor;

        codigos = new int[100];

        notas = new double[100][3];

        cantidad = 0;
    }

    /*
     * Agrega un estudiante a la matriz.
     */
    public void agregarEstudiante(int codigo) {

        if (buscarFila(codigo) != -1) {
            return;
        }

        codigos[cantidad] = codigo;

        for (int i = 0; i < 3; i++) {
            notas[cantidad][i] = 0;
        }

        cantidad++;
    }

    /*
     * Carga todos los estudiantes registrados en la lista.
     */
    public void cargarDesdeLista() {

        LinkedList lista = gestor.getListaSecuencial();

        Nodo actual = lista.getHead();

        while (actual != null) {

            agregarEstudiante(actual.getEstudiante().getCodigo());

            actual = actual.getSig();
        }
    }

    /*
     * Registra una nota.
     */
    public void registrarNota(int codigo, String materia, double nota) {
        try {
            int fila = buscarFila(codigo);
            if (fila == -1) {
                agregarEstudiante(codigo);
                fila = buscarFila(codigo);
            }
            int columna = obtenerColumna(materia);
            if (columna == -1) {
                System.out.println("Materia no válida.");
                return;
            }
            notas[fila][columna] = nota;
        } catch (Exception e) {
            System.out.println("Error al registrar la nota: " + e.getMessage());
        }
    }

    /*
     * Modifica una nota existente.
     */
    public void modificarNota(int codigo, String materia, double nota) {

        int fila = buscarFila(codigo);

        if (fila == -1) {

            System.out.println("Estudiante no encontrado.");

            return;
        }

        int columna = obtenerColumna(materia);

        if (columna == -1) {

            System.out.println("Materia no válida.");

            return;
        }

        notas[fila][columna] = nota;
    }

    /*
     * Consulta una nota.
     */
    public double consultarNota(int codigo, String materia) {

        int fila = buscarFila(codigo);

        if (fila == -1) {
            return -1;
        }

        int columna = obtenerColumna(materia);

        if (columna == -1) {
            return -1;
        }

        return notas[fila][columna];
    }

    /*
     * Calcula el promedio de un estudiante.
     */
    public double calcularPromedio(int codigo) {

        int fila = buscarFila(codigo);

        if (fila == -1) {
            return 0;
        }

        double suma = 0;

        for (int i = 0; i < 3; i++) {
            suma += notas[fila][i];
        }

        return suma / 3;
    }

    /*
     * Calcula el promedio de una materia.
     */
    public double calcularPromedioMateria(String materia) {

        int columna = obtenerColumna(materia);

        if (columna == -1) {
            return 0;
        }

        if (cantidad == 0) {
            return 0;
        }

        double suma = 0;

        for (int i = 0; i < cantidad; i++) {

            suma += notas[i][columna];

        }

        return suma / cantidad;
    }

    /*
     * Devuelve las tres notas de un estudiante.
     */
    public double[] obtenerNotas(int codigo) {

        int fila = buscarFila(codigo);

        if (fila == -1) {
            return null;
        }

        return notas[fila];
    }

    /*
     * Muestra las notas de un estudiante.
     */
    public void mostrarNotas(int codigo) {

        int fila = buscarFila(codigo);

        if (fila == -1) {

            System.out.println("Estudiante no encontrado.");

            return;
        }

        System.out.println("Matematicas : " + notas[fila][0]);
        System.out.println("Programacion: " + notas[fila][1]);
        System.out.println("Fisica      : " + notas[fila][2]);
        System.out.printf("Promedio    : %.2f%n", calcularPromedio(codigo));
    }

    /*
     * Busca la fila correspondiente al código.
     */
    private int buscarFila(int codigo) {

        for (int i = 0; i < cantidad; i++) {

            if (codigos[i] == codigo) {
                return i;
            }

        }

        return -1;
    }

    /*
     * Devuelve la columna de una materia.
     */
    private int obtenerColumna(String materia) {

        if (materia.equalsIgnoreCase(Materia.MATEMATICAS)) {
            return 0;
        }

        if (materia.equalsIgnoreCase(Materia.PROGRAMACION)) {
            return 1;
        }

        if (materia.equalsIgnoreCase(Materia.FISICA)) {
            return 2;
        }

        return -1;
    }

    /*
     * Devuelve la cantidad de estudiantes registrados.
     */
    public int getCantidad() {
        return cantidad;
    }

    /*
     * Devuelve el código almacenado en una posición.
     */
    public int getCodigo(int posicion) {
        return codigos[posicion];
    }

    /*
     * Devuelve todas las notas.
     */
    public double[][] getNotas() {
        return notas;
    }

    /*
     * Devuelve todos los códigos registrados.
     */
    public int[] getCodigos() {
        return codigos;
    }
}