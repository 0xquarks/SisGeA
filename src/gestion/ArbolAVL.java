package gestion;

public class ArbolAVL {
    private NodoAVL raiz;

    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int obtenerBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.izq) - altura(nodo.der);
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;

        x.der = y;
        y.izq = T2;

        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;

        y.izq = x;
        x.der = T2;

        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        return y;
    }

    private NodoAVL rebalancear(NodoAVL nodo, int codigo) {
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        int balance = obtenerBalance(nodo);

        if (balance > 1 && codigo < nodo.izq.dato.getCodigo()) {
            return rotarDerecha(nodo);
        }
        if (balance < -1 && codigo > nodo.der.dato.getCodigo()) {
            return rotarIzquierda(nodo);
        }
        if (balance > 1 && codigo > nodo.izq.dato.getCodigo()) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && codigo < nodo.der.dato.getCodigo()) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public void insertar(Estudiante e) {
        raiz = insertarRec(raiz, e);
    }

    private NodoAVL insertarRec(NodoAVL nodo, Estudiante e) {
        if (nodo == null) return new NodoAVL(e);

        if (e.getCodigo() < nodo.dato.getCodigo()) {
            nodo.izq = insertarRec(nodo.izq, e);
        } else if (e.getCodigo() > nodo.dato.getCodigo()) {
            nodo.der = insertarRec(nodo.der, e);
        } else {
            return nodo;
        }

        return rebalancear(nodo, e.getCodigo());
    }

    public void eliminar(int codigo) {
        raiz = eliminarRec(raiz, codigo);
    }

    private NodoAVL eliminarRec(NodoAVL nodo, int codigo) {
        if (nodo == null) return nodo;

        if (codigo < nodo.dato.getCodigo()) {
            nodo.izq = eliminarRec(nodo.izq, codigo);
        } else if (codigo > nodo.dato.getCodigo()) {
            nodo.der = eliminarRec(nodo.der, codigo);
        } else {
            if ((nodo.izq == null) || (nodo.der == null)) {
                NodoAVL temp = (nodo.izq != null) ? nodo.izq : nodo.der;
                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoAVL temp = nodoMinimo(nodo.der);
                nodo.dato = temp.dato;
                nodo.der = eliminarRec(nodo.der, temp.dato.getCodigo());
            }
        }

        if (nodo == null) return nodo;

        nodo.altura = Math.max(altura(nodo.izq), altura(nodo.der)) + 1;
        int balance = obtenerBalance(nodo);

        if (balance > 1 && obtenerBalance(nodo.izq) >= 0) return rotarDerecha(nodo);
        if (balance > 1 && obtenerBalance(nodo.izq) < 0) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && obtenerBalance(nodo.der) <= 0) return rotarIzquierda(nodo);
        if (balance < -1 && obtenerBalance(nodo.der) > 0) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL nodoMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izq != null) actual = actual.izq;
        return actual;
    }

    public Estudiante buscar(int codigo) {
        return buscarRec(raiz, codigo);
    }

    private Estudiante buscarRec(NodoAVL nodo, int codigo) {
        if (nodo == null) return null;
        if (codigo == nodo.dato.getCodigo()) return nodo.dato;
        
        return (codigo < nodo.dato.getCodigo()) 
            ? buscarRec(nodo.izq, codigo) 
            : buscarRec(nodo.der, codigo);
    }

    public void mostrarArbol() {
        inorden(raiz);
    }

    private void inorden(NodoAVL nodo) {
        if (nodo != null) {
            inorden(nodo.izq);
            System.out.println(nodo.dato);
            inorden(nodo.der);
        }
    }
}