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
 * Consume métodos públicos de negocio y previene fallos ante datos incompletos.
 */
public class PanelReportes extends JPanel {
    private Gestor gestor;
    private GestorCalificaciones gestorCalificaciones;
    private Buscador buscador;
    private RankingAcademico ranking;
    private JTextArea areaResultado;

    public PanelReportes(Gestor gestor, GestorCalificaciones gestorCalificaciones, Buscador buscador, RankingAcademico ranking) {
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

    // Reporte 1: Captura salida de consola de forma segura previniendo fallos de impresión.
    private void reporteListadoCompleto() {
        try {
            String salida = CapturadorConsola.capturar(() -> gestor.listarTodos());
            if (salida == null || salida.trim().isEmpty()) {
                areaResultado.setText("No hay estudiantes registrados para listar.");
            } else {
                areaResultado.setText(salida);
            }
        } catch (Exception e) {
            areaResultado.setText("Error al generar el listado completo: " + e.getMessage());
        }
    }

    // Reporte 2: Valida que el arreglo de estudiantes no sea nulo ni esté vacío antes de iterar.
    private void reportePromediosIndividuales() {
        try {
            Estudiante[] estudiantes = buscador.obtenerArreglo(gestor);
            if (estudiantes == null || estudiantes.length == 0) {
                areaResultado.setText("No hay estudiantes registrados en el sistema.");
                return;
            }
            StringBuilder texto = new StringBuilder("REPORTE DE PROMEDIOS INDIVIDUALES\n\n");
            for (Estudiante estudiante : estudiantes) {
                if (estudiante != null) {
                    double promedio = gestorCalificaciones.calcularPromedio(estudiante.getCodigo());
                    texto.append(String.format("Código: %d | Nombre: %-25s | Promedio: %.2f%n",
                            estudiante.getCodigo(), estudiante.getNombre(), promedio));
                }
            }
            areaResultado.setText(texto.toString());
        } catch (Exception e) {
            areaResultado.setText("Error al calcular los promedios individuales:\n" + e.getMessage());
        }
    }

    // Reporte 3 y 4: Maneja el caso en que el ranking esté vacío o falle su generación.
    private void reporteMejorPeor() {
        try {
            ranking.generarRanking();
            Estudiante mejor = ranking.mejorEstudiante();
            Estudiante peor = ranking.peorEstudiante();

            if (mejor == null && peor == null) {
                areaResultado.setText("No es posible determinar el mejor/peor estudiante (datos insuficientes).");
                return;
            }
            StringBuilder texto = new StringBuilder("REPORTE: MEJOR Y PEOR PROMEDIO\n\n");
            texto.append("Mejor estudiante: ").append(mejor != null ? mejor.getNombre() : "N/D").append("\n");
            texto.append("Peor estudiante : ").append(peor != null ? peor.getNombre() : "N/D");
            areaResultado.setText(texto.toString());
        } catch (Exception e) {
            areaResultado.setText("Error al obtener los extremos académicos:\n" + e.getMessage());
        }
    }

    // Reporte 5: Captura y maneja de forma segura errores al computar el ordenamiento del ranking.
    private void reporteRankingGeneral() {
        try {
            String salida = CapturadorConsola.capturar(() -> {
                ranking.generarRanking();
                ranking.mostrarRanking();
            });
            if (salida == null || salida.trim().isEmpty()) {
                areaResultado.setText("No se pudo estructurar el ranking general.");
            } else {
                areaResultado.setText(salida);
            }
        } catch (Exception e) {
            areaResultado.setText("Error al computar el ranking general:\n" + e.getMessage());
        }
    }
}