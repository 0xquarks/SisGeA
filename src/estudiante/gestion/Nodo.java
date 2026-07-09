package estudiante.gestion;

import estudiante.Estudiante;

/// Esta clase representa el nodo de la lista enalzada que permite agregar y retirar estduiantes.
/// Se hizo uso de una lista enlazada dado que nos permite agregar, eliminar, buscar y modificar rapidamente el codigo de estudiantes.
public class Nodo {
    private Estudiante estudiante;
    private Nodo sig;

    Nodo(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.sig = null;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }
}
