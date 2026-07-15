package interfaz;

import estudiante.gestion.Gestor;
import calificaciones.GestorCalificaciones;
import ranking.RankingAcademico;
import busqueda.Buscador;
import utilidades.Archivos;
import javax.swing.*;
import java.awt.*;

/*
 * Ventana principal del Sistema de Gestión Académica.
 * Ensambla, como capa de presentación, los distintos paneles del
 * sistema sobre las clases de negocio ya existentes.
 */
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        super("Sistema de Gestion Academica (SisGeA)");
        Gestor gestor = new Gestor();
        GestorCalificaciones gestorCalificaciones = new GestorCalificaciones(gestor);

        // Control de errores al cargar los archivos para evitar caídas de la GUI
        try {
            Archivos.loadFile("src/estudiantes.txt", gestor);
            gestorCalificaciones.cargarDesdeLista();
            Archivos.cargarCalificaciones("src/calificaciones.txt", gestorCalificaciones);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los archivos de datos:\n" + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }

        RankingAcademico ranking = new RankingAcademico(gestor, gestorCalificaciones);
        Buscador buscador = new Buscador();
        PanelEstudiantes panelEstudiantes = new PanelEstudiantes(gestor, buscador);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Estudiantes", panelEstudiantes);
        pestanas.addTab("Calificaciones", new PanelCalificaciones(gestorCalificaciones));
        pestanas.addTab("Busquedas", new PanelBusquedas(gestor, buscador));
        pestanas.addTab("Ranking", new PanelRanking(ranking));
        pestanas.addTab("Reportes", new PanelReportes(gestor, gestorCalificaciones, buscador, ranking));

        setJMenuBar(crearMenu(panelEstudiantes));
        setContentPane(pestanas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * Crea la barra de menu superior (Archivo y Ayuda).
     */
    private JMenuBar crearMenu(PanelEstudiantes panelEstudiantes) {
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemRefrescar = new JMenuItem("Refrescar estudiantes");
        itemRefrescar.addActionListener(e -> {
            try {
                panelEstudiantes.refrescarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al refrescar la lista: " + ex.getMessage(),
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemRefrescar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcercaDe = new JMenuItem("Acerca de");
        itemAcercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema de Gestion Academica (SisGeA)\n"
                        + "Gestion de estudiantes, calificaciones, busquedas y ranking.",
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE));

        menuAyuda.add(itemAcercaDe);
        barraMenu.add(menuArchivo);
        barraMenu.add(menuAyuda);
        return barraMenu;
    }
}