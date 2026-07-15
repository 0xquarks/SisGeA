import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import utilidades.Archivos;
import calificaciones.GestorCalificaciones;
import ranking.RankingAcademico;
import busqueda.Buscador;
import interfaz.VentanaPrincipal;
import java.util.Scanner;

public class Main {

    // Método principal que arranca la interfaz gráfica por defecto o la consola si recibe el argumento.
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {
            ejecutarConsola();
        } else {
            javax.swing.SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        }
    }

    // Ejecuta el flujo del menú interactivo en modo consola con el diseño exacto de la guía.
    private static void ejecutarConsola() {
        Gestor gestor = new Gestor();
        GestorCalificaciones gestorCalificaciones = new GestorCalificaciones(gestor);
        Scanner scanner = new Scanner(System.in);

        Archivos.loadFile("src/estudiantes.txt", gestor);
        gestorCalificaciones.cargarDesdeLista();
        Archivos.cargarCalificaciones("src/calificaciones.txt", gestorCalificaciones);

        RankingAcademico ranking = new RankingAcademico(gestor, gestorCalificaciones);
        Buscador buscador = new Buscador();
        int opcion;

        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> registrarEstudiante(scanner, gestor, gestorCalificaciones);
                case 2 -> gestor.listarTodos();
                case 3 -> buscarEstudiante(scanner, gestor);
                case 4 -> eliminarEstudiante(scanner, gestor);
                case 5 -> registrarNota(scanner, gestorCalificaciones);
                case 6 -> modificarNota(scanner, gestorCalificaciones);
                case 7 -> consultarNotas(scanner, gestorCalificaciones);
                case 8 -> calcularPromedio(scanner, gestorCalificaciones);
                case 9 -> { ranking.generarRanking(); ranking.mostrarRanking(); }
                case 10 -> ranking.compararOrdenamientos(); // Compara BubbleSort vs QuickSort.
                case 11 -> buscarPorCodigo(scanner, gestor, buscador); // Búsqueda binaria/lineal con tiempos.
                case 12 -> mostrarArbolAVL(buscador); // Llama a la visualización del árbol.
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcion != 0);
        scanner.close();
    }

    // Muestra visualmente las opciones del menú sugerido por la guía en la consola.
    private static void mostrarMenu() {
        System.out.println("\n======== SISTEMA ACADÉMICO ========");
        System.out.println("1. Registrar estudiante\n2. Mostrar estudiantes\n3. Buscar estudiante\n4. Eliminar estudiante");
        System.out.println("5. Registrar nota\n6. Modificar nota\n7. Consultar notas\n8. Calcular promedio");
        System.out.println("9. Mostrar ranking\n10. Ordenar estudiantes\n11. Buscar estudiante por código\n12. Mostrar árbol");
        System.out.println("0. Salir\nOpcion: ");
    }

    // Captura los datos de consola e inserta un nuevo estudiante en los gestores.
    private static void registrarEstudiante(Scanner sc, Gestor g, GestorCalificaciones gc) {
        System.out.print("Código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        System.out.print("Carrera: ");
        String car = sc.nextLine();
        g.registrarEstudiante(cod, nom, car);
        gc.agregarEstudiante(cod);
    }

    // Solicita un código de estudiante y muestra sus datos en pantalla.
    private static void buscarEstudiante(Scanner sc, Gestor g) {
        System.out.print("Código: ");
        Estudiante est = g.consultarEstudiante(Integer.parseInt(sc.nextLine()));
        System.out.println(est != null ? est : "No encontrado.");
    }

    // Solicita un código para eliminar permanentemente al estudiante.
    private static void eliminarEstudiante(Scanner sc, Gestor g) {
        System.out.print("Código: ");
        g.eliminarEstudiante(Integer.parseInt(sc.nextLine()));
    }

    // Permite al usuario registrar una calificación para una materia específica.
    private static void registrarNota(Scanner sc, GestorCalificaciones gc) {
        System.out.print("Código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.print("Materia: ");
        String mat = sc.nextLine();
        System.out.print("Nota: ");
        double nota = Double.parseDouble(sc.nextLine());
        gc.registrarNota(cod, mat, nota);
    }

    // Modifica una calificación previamente registrada para un estudiante.
    private static void modificarNota(Scanner sc, GestorCalificaciones gc) {
        System.out.print("Código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.print("Materia: ");
        String mat = sc.nextLine();
        System.out.print("Nueva nota: ");
        double nota = Double.parseDouble(sc.nextLine());
        gc.modificarNota(cod, mat, nota);
    }

    // Muestra en consola el historial de calificaciones registradas del estudiante.
    private static void consultarNotas(Scanner sc, GestorCalificaciones gc) {
        System.out.print("Código: ");
        gc.mostrarNotas(Integer.parseInt(sc.nextLine()));
    }

    // Muestra en pantalla el promedio académico general acumulado por el estudiante.
    private static void calcularPromedio(Scanner sc, GestorCalificaciones gc) {
        System.out.print("Código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.println("Promedio: " + gc.calcularPromedio(cod));
    }

    // Realiza búsquedas usando el arreglo de estudiantes para medir y comparar algoritmos.
    private static void buscarPorCodigo(Scanner sc, Gestor g, Buscador b) {
        System.out.print("Código a buscar: ");
        try {
            int cod = Integer.parseInt(sc.nextLine());
            Estudiante[] arr = b.obtenerArreglo(g); // Asegúrate de que el método se llame así en tu clase Buscador
            Estudiante est = b.buscarBinaria(arr, cod);
            System.out.println(est != null ? "Encontrado: " + est : "No encontrado.");
            System.out.println("Comparaciones: " + b.getComparaciones() + " | Tiempo: " + b.getTiempoMs() + "ms");
        } catch (Exception e) {
            System.out.println("Código inválido.");
        }
    }

    // Llama al método encargado de renderizar o imprimir el árbol AVL en la consola.
    private static void mostrarArbolAVL(Buscador b) {
        System.out.println("\n--- ESTRUCTURA DEL ÁRBOL AVL ---");
        // TODO: Asegúrate de que tu clase Buscador (o Gestor) tenga un método para mostrar el árbol.
        // b.mostrarArbol();
    }
}