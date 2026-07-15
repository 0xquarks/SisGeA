package estudiante.almacenamiento;

import estudiante.Estudiante;
import estudiante.gestion.Nodo;

public class ArbolAVL {
    // Nodo raíz que sirve como punto de partida del árbol AVL.
    private NodoAVL raiz;

    // Retorna la altura de un nodo, devolviendo 0 si el nodo es nulo.
    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    // Calcula el factor de equilibrio restando la altura izquierda de la derecha.
    private int obtenerBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.getIzq()) - altura(nodo.getDer());
    }

    // Realiza una rotación simple a la derecha sobre el nodo recibido para balancear el árbol.
    private NodoAVL rotarDerecha(NodoAVL y) {
        if (y == null || y.getIzq() == null) {
            return y;
        }
        NodoAVL x = y.getIzq();
        NodoAVL T2 = x.getDer();

        x.setDer(y);
        y.setIzq(T2);

        y.setAltura(Math.max(altura(y.getIzq()), altura(y.getDer())) + 1);
        x.setAltura(Math.max(altura(x.getIzq()), altura(x.getDer())) + 1);

        return x;
    }

    // Realiza una rotación simple a la izquierda sobre el nodo recibido para balancear el árbol.
    private NodoAVL rotarIzquierda(NodoAVL x) {
        if (x == null || x.getDer() == null) {
            return x;
        }
        NodoAVL y = x.getDer();
        NodoAVL T2 = y.getIzq();

        y.setIzq(x);
        x.setDer(T2);

        x.setAltura(Math.max(altura(x.getIzq()), altura(x.getDer())) + 1);
        y.setAltura(Math.max(altura(y.getIzq()), altura(y.getDer())) + 1);

        return y;
    }

    // Comprueba el balance del nodo y ejecuta las rotaciones necesarias según el caso detectado.
    private NodoAVL rebalancear(NodoAVL nodo, int codigo) {
        if (nodo == null) {
            return null;
        }
        nodo.setAltura(1 + Math.max(altura(nodo.getIzq()), altura(nodo.getDer())));
        int balance = obtenerBalance(nodo);

        if (balance > 1 && nodo.getIzq() != null && codigo < nodo.getIzq().getCodigo()) {
            return rotarDerecha(nodo);
        }
        if (balance < -1 && nodo.getDer() != null && codigo > nodo.getDer().getCodigo()) {
            return rotarIzquierda(nodo);
        }
        if (balance > 1 && nodo.getIzq() != null && codigo > nodo.getIzq().getCodigo()) {
            nodo.setIzq(rotarIzquierda(nodo.getIzq()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && nodo.getDer() != null && codigo < nodo.getDer().getCodigo()) {
            nodo.setDer(rotarDerecha(nodo.getDer()));
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Inserta públicamente un nuevo índice con su respectivo código y referencia de nodo.
    public void insertar(int codigo, Nodo nodoLista) {
        if (nodoLista == null || nodoLista.getEstudiante() == null) {
            return;
        }
        raiz = insertarRec(raiz, codigo, nodoLista);
    }

    // Inserta un nodo de forma recursiva y aplica rebalanceo dinámico de subárboles.
    private NodoAVL insertarRec(NodoAVL nodo, int codigo, Nodo nodoLista) {
        if (nodo == null) return new NodoAVL(codigo, nodoLista);

        if (codigo < nodo.getCodigo()) {
            nodo.setIzq(insertarRec(nodo.getIzq(), codigo, nodoLista));
        } else if (codigo > nodo.getCodigo()) {
            nodo.setDer(insertarRec(nodo.getDer(), codigo, nodoLista));
        } else {
            return nodo;
        }

        return rebalancear(nodo, codigo);
    }

    // Elimina del árbol la indexación de un estudiante según su código.
    public void eliminar(int codigo) {
        raiz = eliminarRec(raiz, codigo);
    }

    // Busca y elimina recursivamente el nodo del árbol, aplicando rebalanceo en el retroceso.
    private NodoAVL eliminarRec(NodoAVL nodo, int codigo) {
        if (nodo == null) return nodo;

        if (codigo < nodo.getCodigo()) {
            nodo.setIzq(eliminarRec(nodo.getIzq(), codigo));
        } else if (codigo > nodo.getCodigo()) {
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
                if (temp != null) {
                    nodo.setCodigo(temp.getCodigo());
                    nodo.setNodo(temp.getNodo());
                    nodo.setDer(eliminarRec(nodo.getDer(), temp.getCodigo()));
                }
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

    // Localiza recursivamente el nodo con el valor de código más pequeño en un subárbol.
    private NodoAVL nodoMinimo(NodoAVL nodo) {
        if (nodo == null) return null;
        NodoAVL actual = nodo;
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    // Busca un código y retorna su referencia directa en la lista secuencial.
    public Nodo buscar(int codigo) {
        return buscarRec(raiz, codigo);
    }

    // Busca de forma recursiva en el árbol de manera logarítmica O(log n).
    private Nodo buscarRec(NodoAVL nodo, int codigo) {
        if (nodo == null) return null;
        if (codigo == nodo.getCodigo()) return nodo.getNodo();

        return (codigo < nodo.getCodigo())
                ? buscarRec(nodo.getIzq(), codigo)
                : buscarRec(nodo.getDer(), codigo);
    }

    // Inicia el proceso de impresión ordenada de los estudiantes almacenados.
    public void mostrarArbol() {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }
        inorden(raiz);
    }

    // Recorre el árbol en inorden para mostrar los estudiantes ordenados ascendentemente por código.
    private void inorden(NodoAVL nodo) {
        if (nodo != null) {
            inorden(nodo.getIzq());
            if (nodo.getNodo() != null && nodo.getNodo().getEstudiante() != null) {
                System.out.println(nodo.getNodo().getEstudiante());
            }
            inorden(nodo.getDer());
        }
    }
}