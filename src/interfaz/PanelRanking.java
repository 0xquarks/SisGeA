package interfaz;

import calificaciones.Materia;
import ranking.RankingAcademico;
import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para el módulo de ranking (Módulo 5).
 * Consume de forma segura los métodos de RankingAcademico y captura
 * su salida para renderizarla dentro del área de texto.
 */
public class PanelRanking extends JPanel {
    private RankingAcademico ranking;
    private JComboBox<String> comboMateria;
    private JTextArea areaResultado;

    public PanelRanking(RankingAcademico ranking) {
        this.ranking = ranking;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearPanelBotones(), BorderLayout.NORTH);
        add(crearPanelResultado(), BorderLayout.CENTER);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Ranking"));

        JPanel filaGeneral = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botonGeneral = new JButton("Ranking general (QuickSort)");
        JButton botonComparar = new JButton("Comparar BubbleSort vs QuickSort");

        botonGeneral.addActionListener(e -> rankingGeneral());
        botonComparar.addActionListener(e -> comparar());

        filaGeneral.add(botonGeneral);
        filaGeneral.add(botonComparar);

        JPanel filaMateria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboMateria = new JComboBox<>(new String[]{
                Materia.MATEMATICAS,
                Materia.PROGRAMACION,
                Materia.FISICA
        });

        JButton botonMateria = new JButton("Ranking por materia");
        botonMateria.addActionListener(e -> rankingPorMateria());

        filaMateria.add(new JLabel("Materia:"));
        filaMateria.add(comboMateria);
        filaMateria.add(botonMateria);

        panel.add(filaGeneral);
        panel.add(filaMateria);
        return panel;
    }

    private JScrollPane crearPanelResultado() {
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        return new JScrollPane(areaResultado);
    }

    // Valida que exista el motor de ranking y captura la salida previniendo excepciones
    private void rankingGeneral() {
        if (ranking == null) {
            areaResultado.setText("Error: El sistema de ranking no está inicializado.");
            return;
        }
        try {
            String salida = CapturadorConsola.capturar(() -> {
                ranking.generarRanking();
                ranking.mostrarRanking();
            });
            areaResultado.setText(salida != null && !salida.trim().isEmpty() ? salida : "No hay datos para generar el ranking.");
        } catch (Exception e) {
            areaResultado.setText("Error al generar el ranking general:\n" + e.getMessage());
        }
    }

    // Controla errores durante la ejecución comparativa de algoritmos de ordenación
    private void comparar() {
        if (ranking == null) {
            areaResultado.setText("Error: El sistema de ranking no está inicializado.");
            return;
        }
        try {
            String salida = CapturadorConsola.capturar(() -> ranking.compararOrdenamientos());
            areaResultado.setText(salida != null && !salida.trim().isEmpty() ? salida : "No se pudo realizar la comparación.");
        } catch (Exception e) {
            areaResultado.setText("Error al comparar algoritmos de ordenamiento:\n" + e.getMessage());
        }
    }

    // Valida la selección de la materia y evita problemas si no hay notas registradas en ella
    private void rankingPorMateria() {
        if (ranking == null) {
            areaResultado.setText("Error: El sistema de ranking no está inicializado.");
            return;
        }
        String materia = (String) comboMateria.getSelectedItem();
        if (materia == null || materia.trim().isEmpty()) {
            areaResultado.setText("Error: Por favor, selecciona una materia válida.");
            return;
        }
        try {
            String salida = CapturadorConsola.capturar(() -> ranking.mostrarRankingPorMateria(materia));
            areaResultado.setText(salida != null && !salida.trim().isEmpty() ? salida : "No hay registros de notas para la materia: " + materia);
        } catch (Exception e) {
            areaResultado.setText("Error al generar el ranking por materia:\n" + e.getMessage());
        }
    }
}