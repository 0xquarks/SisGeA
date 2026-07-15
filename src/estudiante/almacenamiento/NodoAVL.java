package estudiante.almacenamiento;

import estudiante.gestion.Nodo;

// Representa un nodo del árbol AVL que indexa a los estudiantes por su código de forma ordenada.
public class NodoAVL {
    // Código único del estudiante que actúa como clave de búsqueda en el árbol.
    private int codigo;
    // Referencia directa al nodo correspondiente en la lista enlazada secuencial.
    private Nodo nodo;

    // Referencia al hijo izquierdo (códigos menores).
    private NodoAVL izq;
    // Referencia al hijo derecho (códigos mayores).
    private NodoAVL der;
    // Altura actual del nodo, utilizada para calcular el factor de balanceo.
    private int altura;

    // Constructor que inicializa el nodo con su código, su referencia en la lista y una altura base de 1.
    public NodoAVL(int codigo, Nodo nodo) {
        this.codigo = codigo;
        this.nodo = nodo;
        this.altura = 1;
    }

    // Retorna el código de búsqueda del estudiante indexado en este nodo.
    public int getCodigo() {
        return codigo;
    }

    // Modifica el código de búsqueda de este nodo.
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    // Retorna la referencia al nodo equivalente en la lista enlazada.
    public Nodo getNodo() {
        return nodo;
    }

    // Asigna la referencia de este nodo AVL a un nodo específico en la lista enlazada.
    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    // Retorna el subárbol izquierdo.
    public NodoAVL getIzq() {
        return izq;
    }

    // Asigna el subárbol izquierdo.
    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    // Retorna el subárbol derecho.
    public NodoAVL getDer() {
        return der;
    }

    // Asigna el subárbol derecho.
    public void setDer(NodoAVL der) {
        this.der = der;
    }

    // Obtiene el valor de la altura de este nodo.
    public int getAltura() {
        return altura;
    }

    // Actualiza el valor de la altura de este nodo tras una inserción, eliminación o rotación.
    public void setAltura(int altura) {
        this.altura = altura;
    }
}