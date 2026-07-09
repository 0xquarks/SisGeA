import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import utilidades.Archivos;

import calificaciones.GestorCalificaciones;
import calificaciones.Materia;

import ranking.RankingAcademico;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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