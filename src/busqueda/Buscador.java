package busqueda;

import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import estudiante.gestion.LinkedList;
import estudiante.gestion.Nodo;

/*
 * Esta clase implementa la búsqueda lineal y la búsqueda binaria
 * de estudiantes por código, permitiendo comparar el número de
 * comparaciones y el tiempo de ejecución de cada una.
 */
public class Buscador {

    // Número de comparaciones realizadas en la última búsqueda
    private int comparaciones;

    // Tiempo de ejecución de la última búsqueda, en milisegundos
    private long tiempoMs;

    /*
     * Devuelve el número de comparaciones de la última búsqueda realizada.
     */
    public int getComparaciones() {
        return comparaciones;
    }

    /*
     * Devuelve el tiempo, en milisegundos, de la última búsqueda realizada.
     */
    public long getTiempoMs() {
        return tiempoMs;
    }

    /*
     * Construye un arreglo con todos los estudiantes registrados,
     * recorriendo la lista enlazada principal del gestor.
     */
    public Estudiante[] obtenerArreglo(Gestor gestor) {

        LinkedList lista = gestor.getListaSecuencial();

        int contador = 0;
        Nodo actual = lista.getHead();

        while (actual != null) {
            contador++;
            actual = actual.getSig();
        }

        Estudiante[] arreglo = new Estudiante[contador];

        actual = lista.getHead();
        int i = 0;

        while (actual != null) {
            arreglo[i] = actual.getEstudiante();
            actual = actual.getSig();
            i++;
        }

        return arreglo;
    }

    /*
     * Busca un estudiante por código recorriendo el arreglo de forma secuencial.
     * Cuenta las comparaciones y mide el tiempo de ejecución.
     */
    public Estudiante buscarLineal(Estudiante[] arreglo, int codigo) {

        comparaciones = 0;

        long inicio = System.currentTimeMillis();

        Estudiante encontrado = null;

        for (int i = 0; i < arreglo.length; i++) {

            comparaciones++;

            if (arreglo[i].getCodigo() == codigo) {
                encontrado = arreglo[i];
                break;
            }
        }

        tiempoMs = System.currentTimeMillis() - inicio;

        return encontrado;
    }

    /*
     * Busca un estudiante por código utilizando búsqueda binaria.
     * Como la búsqueda binaria exige que los datos estén ordenados por
     * la clave de búsqueda, primero se ordena una copia del arreglo por
     * código (el arreglo original no se modifica, para no afectar a
     * otras partes del sistema, como el ranking).
     * Cuenta las comparaciones y mide el tiempo de ejecución.
     */
    public Estudiante buscarBinaria(Estudiante[] arreglo, int codigo) {

        Estudiante[] ordenado = copiarArreglo(arreglo);

        ordenarPorCodigo(ordenado);

        comparaciones = 0;

        long inicio = System.currentTimeMillis();

        Estudiante encontrado = null;

        int inferior = 0;
        int superior = ordenado.length - 1;

        while (inferior <= superior) {

            int medio = (inferior + superior) / 2;

            comparaciones++;

            if (ordenado[medio].getCodigo() == codigo) {
                encontrado = ordenado[medio];
                break;
            } else if (ordenado[medio].getCodigo() < codigo) {
                inferior = medio + 1;
            } else {
                superior = medio - 1;
            }
        }

        tiempoMs = System.currentTimeMillis() - inicio;

        return encontrado;
    }

    /*
     * Crea una copia del arreglo recibido, para no alterar el orden
     * original al preparar la búsqueda binaria.
     */
    private Estudiante[] copiarArreglo(Estudiante[] arreglo) {

        Estudiante[] copia = new Estudiante[arreglo.length];

        for (int i = 0; i < arreglo.length; i++) {
            copia[i] = arreglo[i];
        }

        return copia;
    }

    /*
     * Ordena un arreglo de estudiantes por código utilizando
     * inserción directa. Es el paso previo necesario para poder
     * aplicar la búsqueda binaria.
     */
    private void ordenarPorCodigo(Estudiante[] arreglo) {

        for (int i = 1; i < arreglo.length; i++) {

            Estudiante actual = arreglo[i];
            int j = i - 1;

            while (j >= 0 && arreglo[j].getCodigo() > actual.getCodigo()) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            arreglo[j + 1] = actual;
        }
    }
}
