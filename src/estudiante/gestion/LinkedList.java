package estudiante.gestion;

import estudiante.Estudiante;

public class LinkedList {
    private Nodo cabeza = null;

    public void insertarAlInicio(Estudiante estudiante) {
        Nodo nuevoNodo = new Nodo(estudiante);
        nuevoNodo.setSig(cabeza);
        cabeza = nuevoNodo;
    }

    public Estudiante buscar(int codigo) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getEstudiante().getCodigo() == codigo) {
                return actual.getEstudiante();
            }
            actual = actual.getSig();
        }
        return null;
    }

    public void listar() {
        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo temporal = cabeza;
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
        if (cabeza == null) return false;

        if (cabeza.getEstudiante().getCodigo() == codigo) {
            cabeza = cabeza.getSig();
            return true;
        }

        Nodo actual = cabeza;
        while (actual.getSig() != null && actual.getSig().getEstudiante().getCodigo() != codigo) {
            actual = actual.getSig();
        }

        if (actual.getSig() != null) {
            actual.setSig(actual.getSig().getSig());
            return true;
        }
        return false;
    }

    public Nodo getCabeza() {
        return cabeza;
    }
}