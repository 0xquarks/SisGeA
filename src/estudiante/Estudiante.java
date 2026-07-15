package estudiante;

// Representa la entidad de un estudiante con sus datos básicos de identificación y carrera.
public class Estudiante {
    // Identificador único o código del estudiante.
    private int codigo;
    // Nombre completo del estudiante.
    private String nombre;
    // Programa académico o carrera que cursa el estudiante.
    private String carrera;

    // Constructor que inicializa un estudiante con su código, nombre y carrera correspondientes.
    public Estudiante(int codigo, String nombre, String carrera) {
        this.codigo = Math.max(0, codigo);
        this.nombre = (nombre != null && !nombre.trim().isEmpty()) ? nombre.trim() : "Desconocido";
        this.carrera = (carrera != null && !carrera.trim().isEmpty()) ? carrera.trim() : "Sin asignar";
    }

    // Retorna el código único de identificación del estudiante.
    public int getCodigo() {
        return codigo;
    }

    // Permite modificar el código de identificación del estudiante.
    public void setCodigo(int codigo) {
        if (codigo >= 0) {
            this.codigo = codigo;
        }
    }

    // Retorna el nombre completo del estudiante.
    public String getNombre() {
        return nombre;
    }

    // Permite actualizar el nombre completo del estudiante.
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    // Retorna la carrera que está cursando el estudiante.
    public String getCarrera() {
        return carrera;
    }

    // Permite actualizar la carrera en la que está inscrito el estudiante.
    public void setCarrera(String carrera) {
        if (carrera != null && !carrera.trim().isEmpty()) {
            this.carrera = carrera.trim();
        }
    }

    // Devuelve una representación legible en texto con los atributos del estudiante.
    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Carrera: " + carrera;
    }
}