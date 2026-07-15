package calificaciones;

import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import estudiante.gestion.LinkedList;
import estudiante.gestion.Nodo;

/*
 * Esta clase administra las calificaciones de los estudiantes.
 * Optimiza las búsquedas utilizando el Árbol AVL del Gestor de estudiantes.
 */
public class GestorCalificaciones {

    // Acceso al gestor principal de estudiantes
    private Gestor gestor;

    // Guarda los códigos de los estudiantes para saber a quién pertenece cada fila
    private int[] codigos;

    // Matriz de notas
    private double[][] notas;

    // Cantidad de estudiantes registrados en la matriz
    private int cantidad;

    /*
     * Inicializa la matriz de notas.
     */
    public GestorCalificaciones(Gestor gestor) {
        this.gestor = gestor;
        this.codigos = new int[100];
        this.notas = new double[100][3];
        this.cantidad = 0;
    }

    /*
     * Agrega un estudiante a la matriz.
     * Ahora verifica la existencia del estudiante en O(log N) usando el AVL.
     */
    public void agregarEstudiante(int codigo) {
        // BENEFICIO AVL: Verificamos si el estudiante existe en el sistema en tiempo O(log N)
        if (gestor.getArbolIndexado().buscar(codigo) == null) {
            System.out.println("Error: El estudiante con código " + codigo + " no existe en el registro general.");
            return;
        }

        // Si ya está registrado en la matriz de calificaciones, salimos
        if (buscarFila(codigo) != -1) {
            return;
        }

        if (cantidad >= 100) {
            System.out.println("Límite de la matriz de calificaciones alcanzado.");
            return;
        }

        codigos[cantidad] = codigo;

        for (int i = 0; i < 3; i++) {
            notas[cantidad][i] = 0;
        }

        cantidad++;
    }

    /*
     * Carga todos los estudiantes registrados en la lista secuencial.
     */
    public void cargarDesdeLista() {
        LinkedList lista = gestor.getListaSecuencial();
        if (lista == null) return;

        Nodo actual = lista.getHead();
        while (actual != null) {
            if (actual.getEstudiante() != null) {
                agregarEstudiante(actual.getEstudiante().getCodigo());
            }
            actual = actual.getSig();
        }
    }

    /*
     * Registra una nota.
     */
    public void registrarNota(int codigo, String materia, double nota) {
        try {
            // BENEFICIO AVL: Si no está en la matriz, agregarEstudiante validará su existencia con el AVL
            int fila = buscarFila(codigo);
            if (fila == -1) {
                agregarEstudiante(codigo);
                fila = buscarFila(codigo);
                if (fila == -1) return; // Si sigue siendo -1, significa que no existía en el árbol AVL
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
     * Muestra las notas de un estudiante, incluyendo sus datos personales (Nombre/Carrera)
     * obtenidos eficientemente del Árbol AVL.
     */
    public void mostrarNotas(int codigo) {
        int fila = buscarFila(codigo);
        if (fila == -1) {
            System.out.println("Estudiante no encontrado en calificaciones.");
            return;
        }

        // BENEFICIO AVL: Obtenemos el estudiante en tiempo O(log N) para mostrar sus datos
        Nodo nodoEstudiante = gestor.getArbolIndexado().buscar(codigo);
        if (nodoEstudiante != null && nodoEstudiante.getEstudiante() != null) {
            Estudiante est = nodoEstudiante.getEstudiante();
            System.out.println("=== Notas de: " + est.getNombre() + " (" + est.getCarrera() + ") ===");
        } else {
            System.out.println("=== Notas del estudiante (Código: " + codigo + ") ===");
        }

        System.out.println("Matematicas : " + notas[fila][0]);
        System.out.println("Programacion: " + notas[fila][1]);
        System.out.println("Fisica      : " + notas[fila][2]);
        System.out.printf("Promedio    : %.2f%n", calcularPromedio(codigo));
    }

    /*
     * Busca la fila correspondiente al código.
     * 
     * BENEFICIO AVL: En lugar de barrer el array secuencialmente O(N), podemos 
     * comprobar al instante si el estudiante existe mediante el árbol AVL. 
     * Nota: Como las notas se guardan en una estructura estática (matriz de 100 filas), 
     * aún necesitamos mapear el código a su índice numérico [0-99]. 
     * No obstante, usar el AVL previene búsquedas innecesarias si el alumno no existe.
     */
    private int buscarFila(int codigo) {
        // Si el Árbol AVL no encuentra al estudiante, no perdemos tiempo recorriendo la matriz
        if (gestor.getArbolIndexado().buscar(codigo) == null) {
            return -1; 
        }

        // Si existe en el AVL, encontramos qué fila tiene asignada en la matriz
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

    // ==========================================
    // Getters y Setters
    // ==========================================
    public int getCantidad() {
        return cantidad;
    }

    public int getCodigo(int posicion) {
        return codigos[posicion];
    }

    public double[][] getNotas() {
        return notas;
    }

    public int[] getCodigos() {
        return codigos;
    }
}