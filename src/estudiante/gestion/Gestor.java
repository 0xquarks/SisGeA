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
        if (arbolIndexado.buscar(codigo) != null) {
            System.out.println("Error: Código duplicado.");
            return;
        }

        Estudiante nuevo = new Estudiante(codigo, nombre, carrera);

        Nodo ref = listaSecuencial.insertar(nuevo);

        arbolIndexado.insertar(codigo, ref);
        System.out.println("Estudiante registrado exitosamente.");
    }

    // Modifica los datos de un estudiante buscando la referencia de su nodo en el árbol.
    public boolean modificarEstudiante(int codigo, String nuevoNombre, String nuevaCarrera) {
        Nodo refNodo = arbolIndexado.buscar(codigo);

        if (refNodo != null) {
            refNodo.getEstudiante().setNombre(nuevoNombre);
            refNodo.getEstudiante().setCarrera(nuevaCarrera);
            return true;
        }
        return false;
    }

    // Elimina un estudiante por código tanto de la lista secuencial como del árbol AVL.
    public boolean eliminarEstudiante(int codigo) {
        boolean deLista = listaSecuencial.eliminar(codigo);
        if (deLista) {
            arbolIndexado.eliminar(codigo);
            return true;
        }
        return false;
    }

    // Obtiene un estudiante en tiempo logarítmico buscando su nodo en el árbol AVL.
    public Estudiante consultarEstudiante(int codigo) {
        Nodo refNodo = arbolIndexado.buscar(codigo);

        return (refNodo != null) ? refNodo.getEstudiante() : null;
    }

    // Imprime en consola la estructura actual del árbol indexado.
    public void listarTodos() {
        arbolIndexado.mostrarArbol();
    }

    // Retorna la lista secuencial para permitir operaciones externas de ranking o recorrido.
    public LinkedList getListaSecuencial() {
        return listaSecuencial;
    }

    // Permite reasignar una nueva lista secuencial de estudiantes.
    public void setListaSecuencial(LinkedList listaSecuencial) {
        this.listaSecuencial = listaSecuencial;
    }

    // Retorna el árbol AVL de indexación rápida.
    public ArbolAVL getArbolIndexado() {
        return arbolIndexado;
    }

    // Permite reasignar una nueva estructura de árbol AVL para el índice.
    public void setArbolIndexado(ArbolAVL arbolIndexado) {
        this.arbolIndexado = arbolIndexado;
    }
}