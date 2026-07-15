package interfaz;

import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import busqueda.Buscador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
 * Panel de presentación para el módulo de gestión de estudiantes.
 * Consume métodos públicos de Gestor y Buscador con protecciones
 * contra punteros nulos y errores de entrada.
 */
public class PanelEstudiantes extends JPanel {
    private Gestor gestor;
    private Buscador buscador;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JTextField campoCarrera;

    public PanelEstudiantes(Gestor gestor, Buscador buscador) {
        this.gestor = gestor;
        this.buscador = buscador;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelFormulario(), BorderLayout.SOUTH);
        refrescarTabla();
    }

    /*
     * Crea la tabla que muestra a todos los estudiantes registrados.
     */
    private JScrollPane crearPanelTabla() {
        String[] columnas = {"Codigo", "Nombre", "Carrera"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.getTableHeader().setReorderingAllowed(false);
        return new JScrollPane(tabla);
    }

    /*
     * Crea el formulario inferior con los campos y botones de acción.
     */
    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del estudiante"));
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(4, 4, 4, 4);
        restricciones.fill = GridBagConstraints.HORIZONTAL;

        campoCodigo = new JTextField(8);
        campoNombre = new JTextField(15);
        campoCarrera = new JTextField(15);

        restricciones.gridx = 0; restricciones.gridy = 0;
        panelFormulario.add(new JLabel("Codigo:"), restricciones);
        restricciones.gridx = 1;
        panelFormulario.add(campoCodigo, restricciones);

        restricciones.gridx = 2;
        panelFormulario.add(new JLabel("Nombre:"), restricciones);
        restricciones.gridx = 3;
        panelFormulario.add(campoNombre, restricciones);

        restricciones.gridx = 4;
        panelFormulario.add(new JLabel("Carrera:"), restricciones);
        restricciones.gridx = 5;
        panelFormulario.add(campoCarrera, restricciones);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botonRegistrar = new JButton("Registrar");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonBuscar = new JButton("Buscar");
        JButton botonArbol = new JButton("Ver arbol (AVL)");
        JButton botonRefrescar = new JButton("Refrescar");

        botonRegistrar.addActionListener(e -> registrar());
        botonModificar.addActionListener(e -> modificar());
        botonEliminar.addActionListener(e -> eliminar());
        botonBuscar.addActionListener(e -> buscar());
        botonArbol.addActionListener(e -> mostrarArbol());
        botonRefrescar.addActionListener(e -> refrescarTabla());

        panelBotones.add(botonRegistrar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonArbol);
        panelBotones.add(botonRefrescar);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.gridwidth = 6;
        panelFormulario.add(panelBotones, restricciones);

        return panelFormulario;
    }

    /*
     * Lee el codigo ingresado y lo valida de manera segura.
     */
    private Integer leerCodigo() {
        String texto = campoCodigo.getText();
        if (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El campo de código no puede estar vacío.",
                    "Código Requerido",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El código debe ser un número entero válido.",
                    "Código Inválido",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void registrar() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        String nombre = campoNombre.getText() != null ? campoNombre.getText().trim() : "";
        String carrera = campoCarrera.getText() != null ? campoCarrera.getText().trim() : "";

        if (nombre.isEmpty() || carrera.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre y la carrera son obligatorios.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (gestor.consultarEstudiante(codigo) != null) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un estudiante con ese código.",
                        "Código duplicado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            gestor.registrarEstudiante(codigo, nombre, carrera);
            limpiarCampos();
            refrescarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificar() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        String nombre = campoNombre.getText() != null ? campoNombre.getText().trim() : "";
        String carrera = campoCarrera.getText() != null ? campoCarrera.getText().trim() : "";

        try {
            boolean exito = gestor.modificarEstudiante(codigo, nombre, carrera);
            if (!exito) {
                JOptionPane.showMessageDialog(this,
                        "No existe un estudiante con ese código.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                limpiarCampos();
                refrescarTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar al estudiante con código " + codigo + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) return;

        try {
            boolean exito = gestor.eliminarEstudiante(codigo);
            if (!exito) {
                JOptionPane.showMessageDialog(this,
                        "No existe un estudiante con ese código.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                limpiarCampos();
                refrescarTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoCodigo.setText("");
        campoNombre.setText("");
        campoCarrera.setText("");
    }

    private void buscar() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        try {
            Estudiante encontrado = gestor.consultarEstudiante(codigo);
            if (encontrado != null) {
                JOptionPane.showMessageDialog(this,
                        encontrado.toString(),
                        "Estudiante encontrado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró un estudiante con ese código.",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Muestra de forma segura la estructura del árbol AVL usando CapturadorConsola.
     */
    private void mostrarArbol() {
        try {
            String salida = CapturadorConsola.capturar(() -> gestor.listarTodos());
            if (salida == null || salida.trim().isEmpty()) {
                salida = "El árbol AVL se encuentra vacío en este momento.";
            }
            JTextArea area = new JTextArea(salida, 20, 40);
            area.setEditable(false);
            area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            JOptionPane.showMessageDialog(this,
                    new JScrollPane(area),
                    "Recorrido del árbol AVL",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al renderizar el árbol: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Reconstruye la tabla previniendo punteros nulos en el arreglo.
     */
    public void refrescarTabla() {
        modeloTabla.setRowCount(0);
        try {
            Estudiante[] estudiantes = buscador.obtenerArreglo(gestor);
            if (estudiantes == null) return;

            for (Estudiante estudiante : estudiantes) {
                if (estudiante != null) {
                    modeloTabla.addRow(new Object[]{
                            estudiante.getCodigo(),
                            estudiante.getNombre(),
                            estudiante.getCarrera()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al refrescar la lista de estudiantes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}