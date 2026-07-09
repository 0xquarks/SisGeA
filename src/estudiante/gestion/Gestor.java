package estudiante.gestion;

import estudiante.Estudiante;
import estudiante.almacenamiento.ArbolAVL;

public class Gestor {
    private LinkedList listaSecuencial = new LinkedList();
    private ArbolAVL arbolIndexado = new ArbolAVL();

    public void registrarEstudiante(int codigo, String nombre, String carrera) {
        if (arbolIndexado.buscar(codigo) != null) {
            System.out.println("Error: Código duplicado.");
            return;
        }

        Estudiante nuevo = new Estudiante(codigo, nombre, carrera);

        Nodo ref = listaSecuencial.insertar(nuevo);

        arbolIndexado.insertar(codigo, ref);
        System.out.println("Estudiante registrado exitosamente.");
    }

    public boolean modificarEstudiante(int codigo, String nuevoNombre, String nuevaCarrera) {
        Nodo refNodo = arbolIndexado.buscar(codigo);

        if (refNodo != null) {
            refNodo.getEstudiante().setNombre(nuevoNombre);
            refNodo.getEstudiante().setCarrera(nuevaCarrera);
            return true;
        }
        return false;
    }

    public boolean eliminarEstudiante(int codigo) {
        boolean deLista = listaSecuencial.eliminar(codigo);
        if (deLista) {
            arbolIndexado.eliminar(codigo);
            return true;
        }
        return false;
    }

    public Estudiante consultarEstudiante(int codigo) {
        Nodo refNodo = arbolIndexado.buscar(codigo);

        return (refNodo != null) ? refNodo.getEstudiante() : null;
    }

    public void listarTodos() {
        arbolIndexado.mostrarArbol();
    }
}