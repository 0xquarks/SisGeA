package gestion;

public class NodoAVL {
    public Estudiante dato;
    public NodoAVL izq;
    public NodoAVL der;
    public int altura;

    public NodoAVL(Estudiante dato) {
        this.dato = dato;
        this.altura = 1;
    }
}