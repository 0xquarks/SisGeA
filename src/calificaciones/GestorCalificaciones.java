package calificaciones;

import estudiante.gestion.Gestor;
import estudiante.gestion.LinkedList;
import estudiante.gestion.Nodo;

/*
 * Esta clase administra las calificaciones de los estudiantes.
 * Cada fila representa un estudiante y cada columna una materia.
 */

public class GestorCalificaciones {

    private Gestor gestor;

    private int[] codigos;

    private double[][] notas;

    private int cantidad;

    public GestorCalificaciones(Gestor gestor) {

        this.gestor = gestor;

        codigos = new int[100];

        notas = new double[100][3];

        cantidad = 0;

    }

    // Agrega un estudiante a la matriz

    public void agregarEstudiante(int codigo) {

        if (buscarFila(codigo) != -1) {
            return;
        }

        codigos[cantidad] = codigo;

        notas[cantidad][0] = 0;

        notas[cantidad][1] = 0;

        notas[cantidad][2] = 0;

        cantidad++;

    }

    // Registra una nota

    public void registrarNota(int codigo, String materia, double nota) {

        int fila = buscarFila(codigo);

        if (fila == -1) {

            agregarEstudiante(codigo);

            fila = buscarFila(codigo);

        }

        int columna = obtenerColumna(materia);

        if (columna != -1) {

            notas[fila][columna] = nota;

        }

    }

    // Modifica una nota

    public void modificarNota(int codigo, String materia, double nota) {

        int fila = buscarFila(codigo);

        if (fila == -1) {

            System.out.println("No existe el estudiante.");

            return;

        }

        int columna = obtenerColumna(materia);

        if (columna != -1) {

            notas[fila][columna] = nota;

        }

    }

    // Consulta una nota

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

    // Calcula el promedio de un estudiante

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

    // Calcula el promedio de una materia

    public double calcularPromedioMateria(String materia) {

        int columna = obtenerColumna(materia);

        if (columna == -1) {

            return 0;

        }

        double suma = 0;

        for (int i = 0; i < cantidad; i++) {

            suma += notas[i][columna];

        }

        if (cantidad == 0) {

            return 0;

        }

        return suma / cantidad;

    }

    // Devuelve las tres notas de un estudiante

    public double[] obtenerNotas(int codigo) {

        int fila = buscarFila(codigo);

        if (fila == -1) {

            return null;

        }

        return notas[fila];

    }
    
    // Busca la fila donde está guardado un estudiante

    private int buscarFila(int codigo) {

        for (int i = 0; i < cantidad; i++) {

            if (codigos[i] == codigo) {

                return i;

            }

        }

        return -1;

    }

    // Devuelve la columna correspondiente a la materia

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

    // Agrega a la matriz todos los estudiantes que existen en la lista

    public void cargarDesdeLista() {

        LinkedList lista = gestor.getListaSecuencial();

        Nodo actual = lista.getHead();

        while (actual != null) {

            agregarEstudiante(actual.getEstudiante().getCodigo());

            actual = actual.getSig();

        }

    }

    // Muestra todas las notas de un estudiante

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

    // Devuelve la cantidad de estudiantes registrados

    public int getCantidad() {

        return cantidad;

    }

    // Devuelve el codigo guardado en una posicion

    public int getCodigo(int posicion) {

        return codigos[posicion];

    }

}