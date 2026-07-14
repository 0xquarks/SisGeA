package interfaz;

import calificaciones.Materia;
import ranking.RankingAcademico;

import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para el módulo de ranking (Módulo 5).
 * Solo consume los métodos públicos de RankingAcademico; no
 * contiene lógica de negocio propia. Los reportes, que la clase
 * de negocio imprime por consola, se capturan con CapturadorConsola
 * para mostrarlos dentro del panel.
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

    private void rankingGeneral() {

        String salida = CapturadorConsola.capturar(() -> {
            ranking.generarRanking();
            ranking.mostrarRanking();
        });

        areaResultado.setText(salida);
    }

    private void comparar() {

        String salida = CapturadorConsola.capturar(
                () -> ranking.compararOrdenamientos());

        areaResultado.setText(salida);
    }

    private void rankingPorMateria() {

        String materia = (String) comboMateria.getSelectedItem();

        String salida = CapturadorConsola.capturar(
                () -> ranking.mostrarRankingPorMateria(materia));

        areaResultado.setText(salida);
    }
}
