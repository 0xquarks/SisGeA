import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import utilidades.Archivos;

import calificaciones.GestorCalificaciones;
import calificaciones.Materia;

import ranking.RankingAcademico;

import busqueda.Buscador;

import interfaz.VentanaPrincipal;

import java.util.Scanner;

public class Main {

    /*
     * Punto de entrada del programa. Por defecto se lanza la
     * interfaz grafica (Swing), que es ahora el medio principal de
     * interaccion con el sistema. El menu de consola original se
     * conserva intacto y puede ejecutarse pasando el argumento
     * "consola" (por ejemplo, util para pruebas rapidas).
     */
    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("consola")) {

            ejecutarConsola();

        } else {

            javax.swing.SwingUtilities.invokeLater(() -> new VentanaPrincipal());

        }
    }

    /*
     * Menu de consola original del proyecto. Se mantiene sin
     * modificaciones como alternativa a la interfaz grafica.
     */
    private static void ejecutarConsola() {

        Gestor gestor = new Gestor();

        GestorCalificaciones gestorCalificaciones =
                new GestorCalificaciones(gestor);

        Scanner scanner = new Scanner(System.in);

        String archivoEstudiantes = "src/estudiantes.txt";

        String archivoCalificaciones = "src/calificaciones.txt";

        System.out.println("Cargando estudiantes...");

        Archivos.loadFile(archivoEstudiantes, gestor);

        gestorCalificaciones.cargarDesdeLista();

        Archivos.cargarCalificaciones(
                archivoCalificaciones,
                gestorCalificaciones
        );

        RankingAcademico ranking =
                new RankingAcademico(
                        gestor,
                        gestorCalificaciones
                );

        Buscador buscador = new Buscador();

        int opcion;

        do {

            System.out.println();

            System.out.println("========= MENU =========");

            System.out.println("1. Registrar estudiante");

            System.out.println("2. Mostrar estudiantes");

            System.out.println("3. Buscar estudiante");

            System.out.println("4. Eliminar estudiante");

            System.out.println("5. Modificar estudiante");

            System.out.println("6. Registrar nota");

            System.out.println("7. Modificar nota");

            System.out.println("8. Consultar nota");

            System.out.println("9. Mostrar notas");

            System.out.println("10. Promedio estudiante");

            System.out.println("11. Promedio materia");

            System.out.println("12. Ranking");

            System.out.println("13. Buscar estudiante (busqueda lineal)");

            System.out.println("14. Buscar estudiante (busqueda binaria)");

            System.out.println("15. Comparar busqueda lineal vs binaria");

            System.out.println("16. Comparar BubbleSort vs QuickSort");

            System.out.println("17. Ranking por materia");

            System.out.println("18. Reporte de promedios individuales");

            System.out.println("0. Salir");

            System.out.print("Opcion: ");

            try {

                opcion = Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {

                opcion = -1;

            }

            switch (opcion) {

                case 1:

                    System.out.print("Codigo: ");

                    int codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.print("Nombre: ");

                    String nombre =
                            scanner.nextLine();

                    System.out.print("Carrera: ");

                    String carrera =
                            scanner.nextLine();

                    gestor.registrarEstudiante(
                            codigo,
                            nombre,
                            carrera
                    );

                    gestorCalificaciones.agregarEstudiante(
                            codigo
                    );

                    break;

                case 2:

                    gestor.listarTodos();

                    break;

                case 3:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    Estudiante estudiante =
                            gestor.consultarEstudiante(codigo);

                    if (estudiante != null) {

                        System.out.println(estudiante);

                    } else {

                        System.out.println("No encontrado.");

                    }

                    break;

                case 4:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    gestor.eliminarEstudiante(codigo);

                    break;

                case 5:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.print("Nuevo nombre: ");

                    nombre =
                            scanner.nextLine();

                    System.out.print("Nueva carrera: ");

                    carrera =
                            scanner.nextLine();

                    gestor.modificarEstudiante(
                            codigo,
                            nombre,
                            carrera
                    );

                    break;

                case 6:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.print("Materia: ");

                    String materia =
                            scanner.nextLine();

                    System.out.print("Nota: ");

                    double nota =
                            Double.parseDouble(scanner.nextLine());

                    gestorCalificaciones.registrarNota(
                            codigo,
                            materia,
                            nota
                    );

                    break;

                case 7:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.print("Materia: ");

                    materia =
                            scanner.nextLine();

                    System.out.print("Nueva nota: ");

                    nota =
                            Double.parseDouble(scanner.nextLine());

                    gestorCalificaciones.modificarNota(
                            codigo,
                            materia,
                            nota
                    );

                    break;

                case 8:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.print("Materia: ");

                    materia =
                            scanner.nextLine();

                    System.out.println(
                            gestorCalificaciones.consultarNota(
                                    codigo,
                                    materia
                            )
                    );

                    break;

                case 9:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    gestorCalificaciones.mostrarNotas(codigo);

                    break;

                case 10:

                    System.out.print("Codigo: ");

                    codigo =
                            Integer.parseInt(scanner.nextLine());

                    System.out.println(
                            "Promedio: "
                                    + gestorCalificaciones.calcularPromedio(codigo)
                    );

                    break;

                case 11:

                    System.out.print("Materia: ");

                    materia =
                            scanner.nextLine();

                    System.out.println(
                            "Promedio: "
                                    + gestorCalificaciones.calcularPromedioMateria(materia)
                    );

                    break;

                case 12:

                    ranking.generarRanking();

                    ranking.mostrarRanking();

                    Estudiante mejor =
                            ranking.mejorEstudiante();

                    Estudiante peor =
                            ranking.peorEstudiante();

                    if (mejor != null) {

                        System.out.println();

                        System.out.println(
                                "Mejor estudiante: "
                                        + mejor.getNombre()
                        );

                    }

                    if (peor != null) {

                        System.out.println(
                                "Peor estudiante: "
                                        + peor.getNombre()
                        );

                    }

                    break;

                case 13:

                    System.out.print("Codigo: ");

                    try {

                        codigo = Integer.parseInt(scanner.nextLine());

                        Estudiante[] arregloLineal =
                                buscador.obtenerArreglo(gestor);

                        Estudiante resultadoLineal =
                                buscador.buscarLineal(arregloLineal, codigo);

                        if (resultadoLineal != null) {

                            System.out.println(resultadoLineal);

                        } else {

                            System.out.println("No encontrado.");

                        }

                        System.out.println(
                                "Comparaciones: "
                                        + buscador.getComparaciones()
                        );

                        System.out.println(
                                "Tiempo (ms)  : "
                                        + buscador.getTiempoMs()
                        );

                    } catch (Exception e) {

                        System.out.println("Codigo invalido.");

                    }

                    break;

                case 14:

                    System.out.print("Codigo: ");

                    try {

                        codigo = Integer.parseInt(scanner.nextLine());

                        Estudiante[] arregloBinaria =
                                buscador.obtenerArreglo(gestor);

                        Estudiante resultadoBinaria =
                                buscador.buscarBinaria(arregloBinaria, codigo);

                        if (resultadoBinaria != null) {

                            System.out.println(resultadoBinaria);

                        } else {

                            System.out.println("No encontrado.");

                        }

                        System.out.println(
                                "Comparaciones: "
                                        + buscador.getComparaciones()
                        );

                        System.out.println(
                                "Tiempo (ms)  : "
                                        + buscador.getTiempoMs()
                        );

                    } catch (Exception e) {

                        System.out.println("Codigo invalido.");

                    }

                    break;

                case 15:

                    System.out.print("Codigo: ");

                    try {

                        codigo = Integer.parseInt(scanner.nextLine());

                        Estudiante[] arregloComparar =
                                buscador.obtenerArreglo(gestor);

                        Estudiante porLineal =
                                buscador.buscarLineal(arregloComparar, codigo);

                        int comparacionesLineal = buscador.getComparaciones();
                        long tiempoLineal = buscador.getTiempoMs();

                        Estudiante porBinaria =
                                buscador.buscarBinaria(arregloComparar, codigo);

                        int comparacionesBinaria = buscador.getComparaciones();
                        long tiempoBinaria = buscador.getTiempoMs();

                        System.out.println();
                        System.out.println("BUSQUEDA LINEAL");

                        System.out.println(
                                "  Resultado     : "
                                        + (porLineal != null ? porLineal.getNombre() : "No encontrado")
                        );

                        System.out.println("  Comparaciones : " + comparacionesLineal);

                        System.out.println("  Tiempo (ms)   : " + tiempoLineal);

                        System.out.println();
                        System.out.println("BUSQUEDA BINARIA");

                        System.out.println(
                                "  Resultado     : "
                                        + (porBinaria != null ? porBinaria.getNombre() : "No encontrado")
                        );

                        System.out.println("  Comparaciones : " + comparacionesBinaria);

                        System.out.println("  Tiempo (ms)   : " + tiempoBinaria);

                    } catch (Exception e) {

                        System.out.println("Codigo invalido.");

                    }

                    break;

                case 16:

                    ranking.compararOrdenamientos();

                    break;

                case 17:

                    System.out.println("1. Matematicas");
                    System.out.println("2. Programacion");
                    System.out.println("3. Fisica");
                    System.out.print("Opcion materia: ");

                    try {

                        int opcionMateria = Integer.parseInt(scanner.nextLine());

                        String materiaElegida;

                        if (opcionMateria == 1) {
                            materiaElegida = Materia.MATEMATICAS;
                        } else if (opcionMateria == 2) {
                            materiaElegida = Materia.PROGRAMACION;
                        } else if (opcionMateria == 3) {
                            materiaElegida = Materia.FISICA;
                        } else {
                            materiaElegida = null;
                        }

                        if (materiaElegida != null) {

                            ranking.mostrarRankingPorMateria(materiaElegida);

                        } else {

                            System.out.println("Opcion invalida.");

                        }

                    } catch (Exception e) {

                        System.out.println("Opcion invalida.");

                    }

                    break;

                case 18:

                    Estudiante[] arregloReporte =
                            buscador.obtenerArreglo(gestor);

                    System.out.println();
                    System.out.println("REPORTE DE PROMEDIOS INDIVIDUALES");

                    for (int i = 0; i < arregloReporte.length; i++) {

                        System.out.printf(
                                "Codigo: %d | Nombre: %s | Promedio: %.2f%n",
                                arregloReporte[i].getCodigo(),
                                arregloReporte[i].getNombre(),
                                gestorCalificaciones.calcularPromedio(
                                        arregloReporte[i].getCodigo())
                        );

                    }

                    break;

                case 0:

                    System.out.println("Fin del programa.");

                    break;

                default:

                    System.out.println("Opcion incorrecta.");

            }

        } while (opcion != 0);

        scanner.close();

    }

}