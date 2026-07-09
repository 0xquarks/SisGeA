import estudiante.gestion.Gestor;
import estudiante.Estudiante;
import utilidades.Archivos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        Scanner scanner = new Scanner(System.in);
        
        String file = "src/estudiantes.txt";
        System.out.println("Cargando datos...");
        Archivos.loadFile(file, gestor);
        System.out.println("------------------------------------------------");

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE ESTUDIANTES ===");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Mostrar Estudiantes");
            System.out.println("3. Buscar estudiante");
            System.out.println("4. Eliminar estudiante");
            System.out.println("5. Modificar Estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese código: ");
                    int codReg = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese nombre: ");
                    String nomReg = scanner.nextLine();
                    System.out.print("Ingrese carrera: ");
                    String carReg = scanner.nextLine();

                    gestor.registrarEstudiante(codReg, nomReg, carReg);

                    System.out.println("Operación finalizada.");
                    break;

                case 2:
                    gestor.listarTodos();
                    break;

                case 3:
                    System.out.print("Ingrese código de estudiante a buscar: ");
                    int codBus = Integer.parseInt(scanner.nextLine());
                    Estudiante est = gestor.consultarEstudiante(codBus);

                    if (est != null) {
                        System.out.println("Encontrado: " + est);
                    } else {
                        System.out.println("Estudiante no registrado.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el código del estudiante a eliminar: ");
                    int codEli = Integer.parseInt(scanner.nextLine());
                    if (gestor.eliminarEstudiante(codEli)) {
                        System.out.println("Estudiante eliminado de ambas estructuras.");
                    } else {
                        System.out.println("Código no encontrado.");
                    }
                    break;

                case 5:
                    System.out.print("Ingrese el código del estudiante a modificar: ");
                    int codMod = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese nuevo nombre: ");
                    String nomMod = scanner.nextLine();
                    System.out.print("Ingrese nueva carrera: ");
                    String carMod = scanner.nextLine();

                    if (gestor.modificarEstudiante(codMod, nomMod, carMod)) {
                        System.out.println("Estudiante modificado con éxito.");
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
            System.out.println("------------------------------------------------");

        } while (opcion != 0);

        scanner.close();
    }
}