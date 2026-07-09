package estudiante.gestion;

import estudiante.Estudiante;
import estudiante.almacenamiento.ArbolAVL;

public class Gestor {
    private LinkedList listaSecuencial = new LinkedList();
    private ArbolAVL arbolIndexado = new ArbolAVL();

    public void registrarEstudiante(int codigo, String nombre, String carrera) {
        if (arbolIndexado.buscar(codigo) != null) {
            System.out.println("Error: Código duplicado.");
            return;
        }

        Estudiante nuevo = new Estudiante(codigo, nombre, carrera);

        listaSecuencial.insertarAlInicio(nuevo);
        arbolIndexado.insertar(nuevo);
    }

    public boolean modificarEstudiante(int codigo, String nuevoNombre, String nuevaCarrera) {
        boolean modificadoEnLista = listaSecuencial.actualizar(codigo, nuevoNombre, nuevaCarrera);

        if (modificadoEnLista) {
            return true;
        }
        return false;
    }

    public boolean eliminarEstudiante(int codigo) {
        boolean deLista = listaSecuencial.eliminar(codigo);
        if (deLista) {
            arbolIndexado.eliminar(codigo);
            return true;
        }
        return false;
    }

    public Estudiante consultarEstudiante(int codigo) {
        return arbolIndexado.buscar(codigo);
    }

    public void listarTodos() {
        arbolIndexado.mostrarArbol();
    }
}