package gestion;

public class GestorEstudiantes {
    private class NodoLista {
        Estudiante estudiante;
        NodoLista siguiente;

        NodoLista(Estudiante estudiante) {
            this.estudiante = estudiante;
            this.siguiente = null;
        }
    }

    private NodoLista cabeza = null;
    private ArbolAVL arbolIndexado = new ArbolAVL();

    public void registrarEstudiante(int codigo, String nombre, String carrera) {
        if (arbolIndexado.buscar(codigo) != null) {
            System.out.println("❌ Error: Ya existe un estudiante con el código " + codigo);
            return;
        }
        
        Estudiante nuevo = new Estudiante(codigo, nombre, carrera);
        
        NodoLista nuevoNodo = new NodoLista(nuevo);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;

        arbolIndexado.insertar(nuevo);
    }

    public boolean modificarEstudiante(int codigo, String nuevoNombre, String nuevaCarrera) {
        Estudiante est = arbolIndexado.buscar(codigo);
        if (est != null) {
            est.setNombre(nuevoNombre);
            est.setCarrera(nuevaCarrera);
            return true;
        }
        return false;
    }

    public boolean eliminarEstudiante(int codigo) {
        if (cabeza == null) return false;

        if (cabeza.estudiante.getCodigo() == codigo) {
            cabeza = cabeza.siguiente;
            arbolIndexado.eliminar(codigo); 
            return true;
        }

        NodoLista actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.estudiante.getCodigo() != codigo) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            arbolIndexado.eliminar(codigo); 
            return true;
        }
        return false;
    }

    public Estudiante consultarEstudiante(int codigo) {
        return arbolIndexado.buscar(codigo);
    }

    public void listarTodos() {
        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        NodoLista temporal = cabeza;
        while (temporal != null) {
            System.out.println(temporal.estudiante);
            temporal = temporal.siguiente;
        }
    }

    public ArbolAVL getArbolIndexado() {
        return arbolIndexado;
    }
}