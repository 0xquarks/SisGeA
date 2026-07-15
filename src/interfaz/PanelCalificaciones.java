package interfaz;

import calificaciones.GestorCalificaciones;
import calificaciones.Materia;
import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para el módulo de calificaciones.
 * Consume los métodos públicos de GestorCalificaciones con validación de datos
 * para evitar estados inconsistentes en la interfaz.
 */
public class PanelCalificaciones extends JPanel {
    private GestorCalificaciones gestorCalificaciones;
    private JTextField campoCodigo;
    private JComboBox<String> comboMateria;
    private JTextField campoNota;
    private JTextArea areaResultado;

    public PanelCalificaciones(GestorCalificaciones gestorCalificaciones) {
        this.gestorCalificaciones = gestorCalificaciones;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearPanelResultado(), BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Calificaciones"));
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(4, 4, 4, 4);
        restricciones.fill = GridBagConstraints.HORIZONTAL;

        campoCodigo = new JTextField(8);
        comboMateria = new JComboBox<>(new String[]{
                Materia.MATEMATICAS,
                Materia.PROGRAMACION,
                Materia.FISICA
        });
        campoNota = new JTextField(6);

        restricciones.gridx = 0; restricciones.gridy = 0;
        panelFormulario.add(new JLabel("Codigo:"), restricciones);
        restricciones.gridx = 1;
        panelFormulario.add(campoCodigo, restricciones);

        restricciones.gridx = 2;
        panelFormulario.add(new JLabel("Materia:"), restricciones);
        restricciones.gridx = 3;
        panelFormulario.add(comboMateria, restricciones);

        restricciones.gridx = 4;
        panelFormulario.add(new JLabel("Nota:"), restricciones);
        restricciones.gridx = 5;
        panelFormulario.add(campoNota, restricciones);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botonRegistrar = new JButton("Registrar nota");
        JButton botonModificar = new JButton("Modificar nota");
        JButton botonConsultar = new JButton("Consultar nota");
        JButton botonNotas = new JButton("Ver notas y promedio");
        JButton botonPromedioMateria = new JButton("Promedio de la materia");

        botonRegistrar.addActionListener(e -> registrarNota());
        botonModificar.addActionListener(e -> modificarNota());
        botonConsultar.addActionListener(e -> consultarNota());
        botonNotas.addActionListener(e -> verNotas());
        botonPromedioMateria.addActionListener(e -> promedioMateria());

        panelBotones.add(botonRegistrar);
        panelBotones.add(botonModificar);
        panelBotones.add(botonConsultar);
        panelBotones.add(botonNotas);
        panelBotones.add(botonPromedioMateria);

        restricciones.gridx = 0; restricciones.gridy = 1; restricciones.gridwidth = 6;
        panelFormulario.add(panelBotones, restricciones);

        return panelFormulario;
    }

    private JScrollPane crearPanelResultado() {
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        return new JScrollPane(areaResultado);
    }

    private Integer leerCodigo() {
        String texto = campoCodigo.getText();
        if (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código no puede estar vacío.", "Código Requerido", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código debe ser un número entero.", "Código Inválido", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private Double leerNota() {
        String texto = campoNota.getText();
        if (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo de nota no puede estar vacío.", "Nota Requerida", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            double nota = Double.parseDouble(texto.trim());
            // Validación adicional de negocio: Evita notas fuera del rango lógico estándar (0.0 a 10.0)
            if (nota < 0.0 || nota > 10.0) {
                JOptionPane.showMessageDialog(this, "La nota debe estar en el rango de 0.0 a 10.0.", "Nota fuera de rango", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return nota;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La nota debe ser un número decimal válido.", "Nota Inválida", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void registrarNota() {
        Integer codigo = leerCodigo();
        Double nota = leerNota();
        if (codigo == null || nota == null) return;

        String materia = (String) comboMateria.getSelectedItem();
        if (materia == null) {
            areaResultado.setText("Error: Selecciona una materia válida.");
            return;
        }

        try {
            gestorCalificaciones.registrarNota(codigo, materia, nota);
            areaResultado.setText("Nota registrada para el código " + codigo + " en " + materia + ".");
            campoNota.setText(""); // Limpia campo tras éxito
        } catch (Exception e) {
            areaResultado.setText("Error al registrar la nota: " + e.getMessage());
        }
    }

    private void modificarNota() {
        Integer codigo = leerCodigo();
        Double nota = leerNota();
        if (codigo == null || nota == null) return;

        String materia = (String) comboMateria.getSelectedItem();
        if (materia == null) {
            areaResultado.setText("Error: Selecciona una materia válida.");
            return;
        }

        try {
            gestorCalificaciones.modificarNota(codigo, materia, nota);
            areaResultado.setText("Nota modificada para el código " + codigo + " en " + materia + ".");
            campoNota.setText(""); // Limpia campo tras éxito
        } catch (Exception e) {
            areaResultado.setText("Error al modificar la nota: " + e.getMessage());
        }
    }

    private void consultarNota() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        String materia = (String) comboMateria.getSelectedItem();
        if (materia == null) {
            areaResultado.setText("Error: Selecciona una materia válida.");
            return;
        }

        try {
            double nota = gestorCalificaciones.consultarNota(codigo, materia);
            if (nota == -1) {
                areaResultado.setText("No se encontró registro de nota para este estudiante en " + materia + ".");
            } else {
                areaResultado.setText("Nota de " + materia + " para el código " + codigo + ": " + nota);
            }
        } catch (Exception e) {
            areaResultado.setText("Error al consultar nota: " + e.getMessage());
        }
    }

    private void verNotas() {
        Integer codigo = leerCodigo();
        if (codigo == null) return;

        try {
            double[] notas = gestorCalificaciones.obtenerNotas(codigo); // O usa obtenerNotas si es el nombre en tu backend
            if (notas == null) {
                areaResultado.setText("Estudiante no encontrado en el registro de calificaciones.");
                return;
            }

            // Verifica que el arreglo tenga el tamaño correcto antes de acceder a sus índices
            double n0 = notas.length > 0 ? notas[0] : 0.0;
            double n1 = notas.length > 1 ? notas[1] : 0.0;
            double n2 = notas.length > 2 ? notas[2] : 0.0;

            StringBuilder texto = new StringBuilder();
            texto.append("Matemáticas : ").append(n0).append("\n");
            texto.append("Programación: ").append(n1).append("\n");
            texto.append("Física      : ").append(n2).append("\n");
            texto.append(String.format("Promedio    : %.2f", gestorCalificaciones.calcularPromedio(codigo)));

            areaResultado.setText(texto.toString());
        } catch (Exception e) {
            areaResultado.setText("Error al recuperar el historial de notas:\n" + e.getMessage());
        }
    }

    private void promedioMateria() {
        String materia = (String) comboMateria.getSelectedItem();
        if (materia == null) {
            areaResultado.setText("Error: Selecciona una materia válida.");
            return;
        }

        try {
            double promedio = gestorCalificaciones.calcularPromedioMateria(materia);
            areaResultado.setText(String.format("Promedio del curso en %s: %.2f", materia, promedio));
        } catch (Exception e) {
            areaResultado.setText("Error al calcular el promedio de la materia:\n" + e.getMessage());
        }
    }
}