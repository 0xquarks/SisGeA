package estudiante.almacenamiento;

import estudiante.Estudiante;

public class NodoAVL {
    private Estudiante dato;

    private NodoAVL izq;
    private NodoAVL der;

    private int altura;

    public NodoAVL(Estudiante dato) {
        this.dato = dato;
        this.altura = 1;
    }

    public Estudiante getDato() {
        return dato;
    }

    public void setDato(Estudiante dato) {
        this.dato = dato;
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