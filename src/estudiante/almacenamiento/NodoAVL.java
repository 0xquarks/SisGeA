package estudiante.almacenamiento;

import estudiante.gestion.Nodo; // Importamos el Nodo de la Lista Enlazada

public class NodoAVL {
    private int codigo;
    private Nodo nodo;

    private NodoAVL izq;
    private NodoAVL der;
    private int altura;

    public NodoAVL(int codigo, Nodo nodo) {
        this.codigo = codigo;
        this.nodo = nodo;
        this.altura = 1;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    public NodoAVL getIzq() {
        return izq;
    }
    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    public NodoAVL getDer() {
        return der;
    }

    public void setDer(NodoAVL der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}