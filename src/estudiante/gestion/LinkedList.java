package estudiante.gestion;

import estudiante.Estudiante;

public class LinkedList {
    private Nodo head = null;

    public Nodo insertar(Estudiante estudiante) {
        Nodo nuevoNodo = new Nodo(estudiante);
        nuevoNodo.setSig(head);
        head = nuevoNodo;

        return nuevoNodo;
    }

    public Estudiante buscar(int codigo) {
        Nodo actual = head;
        while (actual != null) {
            if (actual.getEstudiante().getCodigo() == codigo) {
                return actual.getEstudiante();
            }
            actual = actual.getSig();
        }
        return null;
    }

    public void listar() {
        if (head == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo temporal = head;
        while (temporal != null) {
            System.out.println(temporal.getEstudiante());
            temporal = temporal.getSig();
        }
    }


    public boolean actualizar(int codigo, String nuevoNombre, String nuevaCarrera) {
        Estudiante estudiante = buscar(codigo);
        if (estudiante != null) {
            estudiante.setNombre(nuevoNombre);
            estudiante.setCarrera(nuevaCarrera);
            return true;
        }
        return false;
    }

    public boolean eliminar(int codigo) {
        if (head == null) return false;

        if (head.getEstudiante().getCodigo() == codigo) {
            head = head.getSig();
            return true;
        }

        Nodo actual = head;
        while (actual.getSig() != null && actual.getSig().getEstudiante().getCodigo() != codigo) {
            actual = actual.getSig();
        }

        if (actual.getSig() != null) {
            actual.setSig(actual.getSig().getSig());
            return true;
        }
        return false;
    }

    public Nodo getHead() {
        return head;
    }

    public void insertarAlInicio(Estudiante nuevo) {
    }
}