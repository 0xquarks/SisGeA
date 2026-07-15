package estudiante.gestion;

import estudiante.Estudiante;

// Representa un nodo individual de la lista enlazada para almacenar un estudiante.
public class Nodo {
    // Objeto estudiante almacenado en este nodo.
    private Estudiante estudiante;
    // Referencia al siguiente nodo en la lista enlazada.
    private Nodo sig;

    // Constructor que inicializa el nodo con un estudiante y referencia nula al siguiente.
    Nodo(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.sig = null;
    }

    // Retorna el estudiante almacenado en el nodo.
    public Estudiante getEstudiante() {
        return estudiante;
    }

    // Modifica el estudiante almacenado en este nodo.
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    // Retorna la referencia al siguiente nodo.
    public Nodo getSig() {
        return sig;
    }

    // Establece la referencia hacia el siguiente nodo de la lista.
    public void setSig(Nodo sig) {
        this.sig = sig;
    }
}