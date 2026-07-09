import gestion.GestorEstudiantes;
import gestion.Estudiante;
import utils.Loaders;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorEstudiantes gestor = new GestorEstudiantes();
        Scanner scanner = new Scanner(System.in);
        
        String rutaArchivo = "src/estudiantes.txt";
        System.out.println("🔄 Iniciando sistema y cargando datos...");
        Loaders.cargarEstudiantes(rutaArchivo, gestor);
        System.out.println("------------------------------------------------");

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE ESTUDIANTES ===");
            System.out.println("1. Registrar nuevo estudiante");
            System.out.println("2. Modificar estudiante existente");
            System.out.println("3. Eliminar estudiante");
            System.out.println("4. Consultar estudiante (Búsqueda AVL)");
            System.out.println("5. Listar todos (Estructura Lista)");
            System.out.println("6. Mostrar Árbol AVL (Recorrido Inorden)");
            System.out.println("7. Salir");
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
                    System.out.println("✅ Operación finalizada.");
                    break;

                case 2:
                    System.out.print("Ingrese el código del estudiante a modificar: ");
                    int codMod = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese nuevo nombre: ");
                    String nomMod = scanner.nextLine();
                    System.out.print("Ingrese nueva carrera: ");
                    String carMod = scanner.nextLine();
                    
                    if (gestor.modificarEstudiante(codMod, nomMod, carMod)) {
                        System.out.println("✅ Estudiante modificado con éxito.");
                    } else {
                        System.out.println("❌ Estudiante no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el código del estudiante a eliminar: ");
                    int codEli = Integer.parseInt(scanner.nextLine());
                    if (gestor.eliminarEstudiante(codEli)) {
                        System.out.println("✅ Estudiante eliminado de ambas estructuras.");
                    } else {
                        System.out.println("❌ Código no encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese código a buscar en AVL: ");
                    int codBus = Integer.parseInt(scanner.nextLine());
                    long inicioBusqueda = System.nanoTime();
                    Estudiante est = gestor.consultarEstudiante(codBus);
                    long finBusqueda = System.nanoTime();
                    
                    if (est != null) {
                        System.out.println("🔍 Encontrado: " + est);
                        System.out.println("⏱️ Tiempo de respuesta AVL: " + (finBusqueda - inicioBusqueda) + " ns");
                    } else {
                        System.out.println("❌ Estudiante no registrado.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- LISTA SIMPLEMENTE ENLAZADA (Orden de inserción) ---");
                    gestor.listarTodos();
                    break;

                case 6:
                    System.out.println("\n--- ÁRBOL AVL BALANCEADO (Ordenado por Código) ---");
                    gestor.getArbolIndexado().mostrarArbol();
                    break;

                case 7:
                    System.out.println("👋 Saliendo del sistema...");
                    break;

                default:
                    System.out.println("⚠️ Opción inválida. Intente de nuevo.");
            }
            System.out.println("------------------------------------------------");

        } while (opcion != 7);

        scanner.close();
    }
}