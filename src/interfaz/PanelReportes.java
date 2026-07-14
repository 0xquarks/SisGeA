package interfaz;

import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import calificaciones.GestorCalificaciones;
import busqueda.Buscador;
import ranking.RankingAcademico;

import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para los reportes del sistema (Módulo 6).
 * Solo consume métodos públicos ya existentes de Gestor,
 * GestorCalificaciones, Buscador y RankingAcademico; no contiene
 * lógica de negocio propia.
 */
public class PanelReportes extends JPanel {

    private Gestor gestor;

    private GestorCalificaciones gestorCalificaciones;

    private Buscador buscador;

    private RankingAcademico ranking;

    private JTextArea areaResultado;

    public PanelReportes(Gestor gestor,
                         GestorCalificaciones gestorCalificaciones,
                         Buscador buscador,
                         RankingAcademico ranking) {

        this.gestor = gestor;

        this.gestorCalificaciones = gestorCalificaciones;

        this.buscador = buscador;

        this.ranking = ranking;

        setLayout(new BorderLayout(10, 10));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearPanelBotones(), BorderLayout.NORTH);

        add(crearPanelResultado(), BorderLayout.CENTER);
    }

    private JPanel crearPanelBotones() {

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createTitledBorder("Reportes del sistema"));

        JButton reporte1 = new JButton("Reporte 1: Listado completo de estudiantes");
        JButton reporte2 = new JButton("Reporte 2: Promedios individuales");
        JButton reporte3y4 = new JButton("Reporte 3 y 4: Mejor y peor promedio");
        JButton reporte5 = new JButton("Reporte 5: Ranking general");

        reporte1.setAlignmentX(Component.LEFT_ALIGNMENT);
        reporte2.setAlignmentX(Component.LEFT_ALIGNMENT);
        reporte3y4.setAlignmentX(Component.LEFT_ALIGNMENT);
        reporte5.setAlignmentX(Component.LEFT_ALIGNMENT);

        reporte1.addActionListener(e -> reporteListadoCompleto());
        reporte2.addActionListener(e -> reportePromediosIndividuales());
        reporte3y4.addActionListener(e -> reporteMejorPeor());
        reporte5.addActionListener(e -> reporteRankingGeneral());

        panel.add(reporte1);
        panel.add(Box.createVerticalStrut(4));
        panel.add(reporte2);
        panel.add(Box.createVerticalStrut(4));
        panel.add(reporte3y4);
        panel.add(Box.createVerticalStrut(4));
        panel.add(reporte5);

        return panel;
    }

    private JScrollPane crearPanelResultado() {

        areaResultado = new JTextArea();

        areaResultado.setEditable(false);

        areaResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        return new JScrollPane(areaResultado);
    }

    /*
     * Reporte 1: reutiliza Gestor.listarTodos(), capturando su salida.
     */
    private void reporteListadoCompleto() {

        String salida = CapturadorConsola.capturar(() -> gestor.listarTodos());

        areaResultado.setText(salida);
    }

    /*
     * Reporte 2: promedio individual de cada estudiante, calculado con
     * los metodos publicos ya existentes (Buscador y GestorCalificaciones).
     */
    private void reportePromediosIndividuales() {

        Estudiante[] estudiantes = buscador.obtenerArreglo(gestor);

        StringBuilder texto = new StringBuilder();

        texto.append("REPORTE DE PROMEDIOS INDIVIDUALES\n\n");

        for (Estudiante estudiante : estudiantes) {

            texto.append(String.format(
                    "Codigo: %d | Nombre: %s | Promedio: %.2f%n",
                    estudiante.getCodigo(),
                    estudiante.getNombre(),
                    gestorCalificaciones.calcularPromedio(estudiante.getCodigo())
            ));
        }

        areaResultado.setText(texto.toString());
    }

    /*
     * Reporte 3 y 4: mejor y peor estudiante, reutilizando
     * RankingAcademico.generarRanking()/mejorEstudiante()/peorEstudiante().
     */
    private void reporteMejorPeor() {

        ranking.generarRanking();

        Estudiante mejor = ranking.mejorEstudiante();

        Estudiante peor = ranking.peorEstudiante();

        StringBuilder texto = new StringBuilder();

        texto.append("REPORTE: MEJOR Y PEOR PROMEDIO\n\n");

        if (mejor != null) {
            texto.append("Mejor estudiante: ").append(mejor.getNombre()).append("\n");
        }

        if (peor != null) {
            texto.append("Peor estudiante : ").append(peor.getNombre());
        }

        areaResultado.setText(texto.toString());
    }

    /*
     * Reporte 5: ranking general, reutilizando
     * RankingAcademico.generarRanking()/mostrarRanking() capturados.
     */
    private void reporteRankingGeneral() {

        String salida = CapturadorConsola.capturar(() -> {
            ranking.generarRanking();
            ranking.mostrarRanking();
        });

        areaResultado.setText(salida);
    }
}
