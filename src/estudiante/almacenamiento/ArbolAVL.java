package estudiante.almacenamiento;

import estudiante.Estudiante;

public class ArbolAVL {
    private NodoAVL raiz;

    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private int obtenerBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.getIzq()) - altura(nodo.getDer());
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getIzq();
        NodoAVL T2 = x.getDer();

        x.setDer(y);
        y.setIzq(T2);

        y.setAltura(Math.max(altura(y.getIzq()), altura(y.getDer())) + 1);
        x.setAltura(Math.max(altura(x.getIzq()), altura(x.getDer())) + 1);

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getDer();
        NodoAVL T2 = y.getIzq();

        y.setIzq(x);
        x.setDer(T2);

        x.setAltura(Math.max(altura(x.getIzq()), altura(x.getDer())) + 1);
        y.setAltura(Math.max(altura(y.getIzq()), altura(y.getDer())) + 1);

        return y;
    }

    private NodoAVL rebalancear(NodoAVL nodo, int codigo) {
        nodo.setAltura(1 + Math.max(altura(nodo.getIzq()), altura(nodo.getDer())));
        int balance = obtenerBalance(nodo);

        if (balance > 1 && codigo < nodo.getIzq().getDato().getCodigo()) {
            return rotarDerecha(nodo);
        }
        if (balance < -1 && codigo > nodo.getDer().getDato().getCodigo()) {
            return rotarIzquierda(nodo);
        }
        if (balance > 1 && codigo > nodo.getIzq().getDato().getCodigo()) {
            nodo.setIzq(rotarIzquierda(nodo.getIzq()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && codigo < nodo.getDer().getDato().getCodigo()) {
            nodo.setDer(rotarDerecha(nodo.getDer()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public void insertar(Estudiante e) {
        raiz = insertarRec(raiz, e);
    }

    private NodoAVL insertarRec(NodoAVL nodo, Estudiante e) {
        if (nodo == null) return new NodoAVL(e);

        if (e.getCodigo() < nodo.getDato().getCodigo()) {
            nodo.setIzq(insertarRec(nodo.getIzq(), e));
        } else if (e.getCodigo() > nodo.getDato().getCodigo()) {
            nodo.setDer(insertarRec(nodo.getDer(), e));
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

        if (codigo < nodo.getDato().getCodigo()) {
            nodo.setIzq(eliminarRec(nodo.getIzq(), codigo));
        } else if (codigo > nodo.getDato().getCodigo()) {
            nodo.setDer(eliminarRec(nodo.getDer(), codigo));
        } else {
            if ((nodo.getIzq() == null) || (nodo.getDer() == null)) {
                NodoAVL temp = (nodo.getIzq() != null) ? nodo.getIzq() : nodo.getDer();
                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoAVL temp = nodoMinimo(nodo.getDer());
                nodo.setDato(temp.getDato());
                nodo.setDer(eliminarRec(nodo.getDer(), temp.getDato().getCodigo()));
            }
        }

        if (nodo == null) return nodo;

        nodo.setAltura(Math.max(altura(nodo.getIzq()), altura(nodo.getDer())) + 1);
        int balance = obtenerBalance(nodo);

        if (balance > 1 && obtenerBalance(nodo.getIzq()) >= 0) return rotarDerecha(nodo);
        if (balance > 1 && obtenerBalance(nodo.getIzq()) < 0) {
            nodo.setIzq(rotarIzquierda(nodo.getIzq()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && obtenerBalance(nodo.getDer()) <= 0) return rotarIzquierda(nodo);
        if (balance < -1 && obtenerBalance(nodo.getDer()) > 0) {
            nodo.setDer(rotarDerecha(nodo.getDer()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL nodoMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    public Estudiante buscar(int codigo) {
        return buscarRec(raiz, codigo);
    }

    private Estudiante buscarRec(NodoAVL nodo, int codigo) {
        if (nodo == null) return null;
        if (codigo == nodo.getDato().getCodigo()) return nodo.getDato();

        return (codigo < nodo.getDato().getCodigo())
                ? buscarRec(nodo.getIzq(), codigo)
                : buscarRec(nodo.getDer(), codigo);
    }

    public void mostrarArbol() {
        inorden(raiz);
    }

    private void inorden(NodoAVL nodo) {
        if (nodo != null) {
            inorden(nodo.getIzq());
            System.out.println(nodo.getDato());
            inorden(nodo.getDer());
        }
    }
}