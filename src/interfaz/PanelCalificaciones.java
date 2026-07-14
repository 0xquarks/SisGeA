package interfaz;

import calificaciones.GestorCalificaciones;
import calificaciones.Materia;

import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para el módulo de calificaciones.
 * Solo consume los métodos públicos de GestorCalificaciones; no
 * contiene lógica de negocio propia.
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

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Calificaciones"));

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

        restricciones.gridx = 0;
        restricciones.gridy = 0;
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

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.gridwidth = 6;
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

        try {

            return Integer.parseInt(campoCodigo.getText().trim());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "El codigo debe ser un numero entero.",
                    "Codigo invalido",
                    JOptionPane.WARNING_MESSAGE);

            return null;
        }
    }

    private Double leerNota() {

        try {

            return Double.parseDouble(campoNota.getText().trim());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "La nota debe ser un numero valido.",
                    "Nota invalida",
                    JOptionPane.WARNING_MESSAGE);

            return null;
        }
    }

    private void registrarNota() {

        Integer codigo = leerCodigo();

        Double nota = leerNota();

        if (codigo == null || nota == null) {
            return;
        }

        String materia = (String) comboMateria.getSelectedItem();

        gestorCalificaciones.registrarNota(codigo, materia, nota);

        areaResultado.setText("Nota registrada para el codigo " + codigo
                + " en " + materia + ".");
    }

    private void modificarNota() {

        Integer codigo = leerCodigo();

        Double nota = leerNota();

        if (codigo == null || nota == null) {
            return;
        }

        String materia = (String) comboMateria.getSelectedItem();

        gestorCalificaciones.modificarNota(codigo, materia, nota);

        areaResultado.setText("Nota modificada para el codigo " + codigo
                + " en " + materia + ".");
    }

    private void consultarNota() {

        Integer codigo = leerCodigo();

        if (codigo == null) {
            return;
        }

        String materia = (String) comboMateria.getSelectedItem();

        double nota = gestorCalificaciones.consultarNota(codigo, materia);

        if (nota == -1) {

            areaResultado.setText("No se encontro esa nota.");

        } else {

            areaResultado.setText(
                    "Nota de " + materia + " para el codigo "
                            + codigo + ": " + nota);
        }
    }

    /*
     * Muestra las tres notas y el promedio de un estudiante, utilizando
     * los mismos datos que Gestor.mostrarNotas(), pero formateados
     * mediante los metodos publicos de GestorCalificaciones para poder
     * mostrarlos en el panel en lugar de la consola.
     */
    private void verNotas() {

        Integer codigo = leerCodigo();

        if (codigo == null) {
            return;
        }

        double[] notas = gestorCalificaciones.obtenerNotas(codigo);

        if (notas == null) {

            areaResultado.setText("Estudiante no encontrado.");

            return;
        }

        StringBuilder texto = new StringBuilder();

        texto.append("Matematicas : ").append(notas[0]).append("\n");
        texto.append("Programacion: ").append(notas[1]).append("\n");
        texto.append("Fisica      : ").append(notas[2]).append("\n");
        texto.append(String.format("Promedio    : %.2f",
                gestorCalificaciones.calcularPromedio(codigo)));

        areaResultado.setText(texto.toString());
    }

    private void promedioMateria() {

        String materia = (String) comboMateria.getSelectedItem();

        double promedio = gestorCalificaciones.calcularPromedioMateria(materia);

        areaResultado.setText(String.format(
                "Promedio del curso en %s: %.2f", materia, promedio));
    }
}
