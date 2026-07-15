package estudiante.gestion;

import estudiante.Estudiante;
import estudiante.almacenamiento.ArbolAVL;

public class Gestor {
    // Lista para el almacenamiento secuencial de los estudiantes.
    private LinkedList listaSecuencial = new LinkedList();
    // Árbol AVL para indexar y buscar estudiantes rápidamente por su código.
    private ArbolAVL arbolIndexado = new ArbolAVL();

    // Registra un nuevo estudiante en la lista y lo indexa en el árbol si el código no existe.
    public void registrarEstudiante(int codigo, String nombre, String carrera) {
        if (nombre == null || nombre.trim().isEmpty() || carrera == null || carrera.trim().isEmpty()) {
            System.out.println("Error: Nombre o carrera inválidos.");
            return;
        }
        if (arbolIndexado == null || listaSecuencial == null) {
            System.out.println("Error: Componentes de almacenamiento no inicializados.");
            return;
        }
        if (arbolIndexado.buscar(codigo) != null) {
            System.out.println("Error: Código duplicado.");
            return;
        }

        Estudiante nuevo = new Estudiante(codigo, nombre.trim(), carrera.trim());
        Nodo ref = listaSecuencial.insertar(nuevo);

        if (ref != null) {
            arbolIndexado.insertar(codigo, ref);
            System.out.println("Estudiante registrado exitosamente.");
        } else {
            System.out.println("Error al insertar en la lista secuencial.");
        }
    }

    // Modifica los datos de un estudiante buscando la referencia de su nodo en el árbol.
    public boolean modificarEstudiante(int codigo, String nuevoNombre, String nuevaCarrera) {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevaCarrera == null || nuevaCarrera.trim().isEmpty()) {
            return false;
        }
        if (arbolIndexado == null) return false;

        Nodo refNodo = arbolIndexado.buscar(codigo);
        if (refNodo != null && refNodo.getEstudiante() != null) {
            refNodo.getEstudiante().setNombre(nuevoNombre.trim());
            refNodo.getEstudiante().setCarrera(nuevaCarrera.trim());
            return true;
        }
        return false;
    }

    // Elimina un estudiante por código tanto de la lista secuencial como del árbol AVL.
    public boolean eliminarEstudiante(int codigo) {
        if (listaSecuencial == null || arbolIndexado == null) return false;

        boolean deLista = listaSecuencial.eliminar(codigo);
        if (deLista) {
            arbolIndexado.eliminar(codigo);
            return true;
        }
        return false;
    }

    // Obtiene un estudiante en tiempo logarítmico buscando su nodo en el árbol AVL.
    public Estudiante consultarEstudiante(int codigo) {
        if (arbolIndexado == null) return null;

        Nodo refNodo = arbolIndexado.buscar(codigo);
        return (refNodo != null) ? refNodo.getEstudiante() : null;
    }

    // Imprime en consola la estructura actual del árbol indexado.
    public void listarTodos() {
        if (arbolIndexado != null) {
            arbolIndexado.mostrarArbol();
        } else {
            System.out.println("Error: El árbol de indexación no está inicializado.");
        }
    }

    // Retorna la lista secuencial para permitir operaciones externas de ranking o recorrido.
    public LinkedList getListaSecuencial() {
        return listaSecuencial;
    }

    // Permite reasignar una nueva lista secuencial de estudiantes.
    public void setListaSecuencial(LinkedList listaSecuencial) {
        if (listaSecuencial != null) {
            this.listaSecuencial = listaSecuencial;
        }
    }

    // Retorna el árbol AVL de indexación rápida.
    public ArbolAVL getArbolIndexado() {
        return arbolIndexado;
    }

    // Permite reasignar una nueva estructura de árbol AVL para el índice.
    public void setArbolIndexado(ArbolAVL arbolIndexado) {
        if (arbolIndexado != null) {
            this.arbolIndexado = arbolIndexado;
        }
    }
}