package estudiante.gestion;

import estudiante.Estudiante;

public class LinkedList {
    // Nodo inicial que representa la cabeza de la lista enlazada.
    private Nodo head = null;

    // Inserta un nuevo estudiante al inicio de la lista y retorna el nodo creado.
    public Nodo insertar(Estudiante estudiante) {
        if (estudiante == null) {
            return null;
        }
        Nodo nuevoNodo = new Nodo(estudiante);
        nuevoNodo.setSig(head);
        head = nuevoNodo;

        return nuevoNodo;
    }

    // Recorre la lista secuencialmente para buscar un estudiante por su código.
    public Estudiante buscar(int codigo) {
        Nodo actual = head;
        while (actual != null) {
            if (actual.getEstudiante() != null && actual.getEstudiante().getCodigo() == codigo) {
                return actual.getEstudiante();
            }
            actual = actual.getSig();
        }
        return null;
    }

    // Imprime en consola la información de todos los estudiantes de la lista.
    public void listar() {
        if (head == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo temporal = head;
        while (temporal != null) {
            if (temporal.getEstudiante() != null) {
                System.out.println(temporal.getEstudiante());
            }
            temporal = temporal.getSig();
        }
    }

    // Busca un estudiante por código y actualiza su nombre y carrera.
    public boolean actualizar(int codigo, String nuevoNombre, String nuevaCarrera) {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevaCarrera == null || nuevaCarrera.trim().isEmpty()) {
            return false;
        }
        Estudiante estudiante = buscar(codigo);
        if (estudiante != null) {
            estudiante.setNombre(nuevoNombre.trim());
            estudiante.setCarrera(nuevaCarrera.trim());
            return true;
        }
        return false;
    }

    // Elimina el estudiante que coincida con el código desenlazando su nodo de la lista.
    public boolean eliminar(int codigo) {
        if (head == null) return false;

        if (head.getEstudiante() != null && head.getEstudiante().getCodigo() == codigo) {
            head = head.getSig();
            return true;
        }

        Nodo actual = head;
        while (actual.getSig() != null) {
            Estudiante sigEstudiante = actual.getSig().getEstudiante();
            if (sigEstudiante != null && sigEstudiante.getCodigo() == codigo) {
                actual.setSig(actual.getSig().getSig());
                return true;
            }
            actual = actual.getSig();
        }
        return false;
    }

    // Retorna el primer nodo de la lista enlazada.
    public Nodo getHead() {
        return head;
    }

    // Método auxiliar para insertar un estudiante directamente al principio de la lista.
    public void insertarAlInicio(Estudiante nuevo) {
        if (nuevo != null) {
            insertar(nuevo);
        }
    }
}