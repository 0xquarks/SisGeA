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
        this.codigo = codigo;
        this.nombre = nombre;
        this.carrera = carrera;
    }

    // Retorna el código único de identificación del estudiante.
    public int getCodigo() {
        return codigo;
    }

    // Permite modificar el código de identificación del estudiante.
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    // Retorna el nombre completo del estudiante.
    public String getNombre() {
        return nombre;
    }

    // Permite actualizar el nombre completo del estudiante.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Retorna la carrera que está cursando el estudiante.
    public String getCarrera() {
        return carrera;
    }

    // Permite actualizar la carrera en la que está inscrito el estudiante.
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    // Devuelve una representación legible en texto con los atributos del estudiante.
    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Carrera: " + carrera;
    }
}