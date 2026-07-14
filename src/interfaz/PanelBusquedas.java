package interfaz;

import estudiante.Estudiante;
import estudiante.gestion.Gestor;
import busqueda.Buscador;

import javax.swing.*;
import java.awt.*;

/*
 * Panel de presentación para el módulo de búsquedas (Módulo 6).
 * Solo consume los métodos públicos de Buscador; no contiene
 * lógica de negocio propia.
 */
public class PanelBusquedas extends JPanel {

    private Gestor gestor;

    private Buscador buscador;

    private JTextField campoCodigo;

    private JTextArea areaResultado;

    public PanelBusquedas(Gestor gestor, Buscador buscador) {

        this.gestor = gestor;

        this.buscador = buscador;

        setLayout(new BorderLayout(10, 10));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearPanelFormulario(), BorderLayout.NORTH);

        add(crearPanelResultado(), BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {

        JPanel panelFormulario = new JPanel(new GridBagLayout());

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Buscar estudiante por codigo"));

        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.insets = new Insets(4, 4, 4, 4);

        restricciones.fill = GridBagConstraints.HORIZONTAL;

        campoCodigo = new JTextField(8);

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        panelFormulario.add(new JLabel("Codigo:"), restricciones);

        restricciones.gridx = 1;
        panelFormulario.add(campoCodigo, restricciones);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton botonLineal = new JButton("Busqueda lineal");
        JButton botonBinaria = new JButton("Busqueda binaria");
        JButton botonComparar = new JButton("Comparar ambas");

        botonLineal.addActionListener(e -> buscarLineal());
        botonBinaria.addActionListener(e -> buscarBinaria());
        botonComparar.addActionListener(e -> compararBusquedas());

        panelBotones.add(botonLineal);
        panelBotones.add(botonBinaria);
        panelBotones.add(botonComparar);

        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.gridwidth = 2;
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

    private void buscarLineal() {

        Integer codigo = leerCodigo();

        if (codigo == null) {
            return;
        }

        Estudiante[] arreglo = buscador.obtenerArreglo(gestor);

        Estudiante resultado = buscador.buscarLineal(arreglo, codigo);

        StringBuilder texto = new StringBuilder();

        texto.append("BUSQUEDA LINEAL\n");
        texto.append("Resultado     : ")
                .append(resultado != null ? resultado.toString() : "No encontrado")
                .append("\n");
        texto.append("Comparaciones : ").append(buscador.getComparaciones()).append("\n");
        texto.append("Tiempo (ms)   : ").append(buscador.getTiempoMs());

        areaResultado.setText(texto.toString());
    }

    private void buscarBinaria() {

        Integer codigo = leerCodigo();

        if (codigo == null) {
            return;
        }

        Estudiante[] arreglo = buscador.obtenerArreglo(gestor);

        Estudiante resultado = buscador.buscarBinaria(arreglo, codigo);

        StringBuilder texto = new StringBuilder();

        texto.append("BUSQUEDA BINARIA\n");
        texto.append("Resultado     : ")
                .append(resultado != null ? resultado.toString() : "No encontrado")
                .append("\n");
        texto.append("Comparaciones : ").append(buscador.getComparaciones()).append("\n");
        texto.append("Tiempo (ms)   : ").append(buscador.getTiempoMs());

        areaResultado.setText(texto.toString());
    }

    private void compararBusquedas() {

        Integer codigo = leerCodigo();

        if (codigo == null) {
            return;
        }

        Estudiante[] arreglo = buscador.obtenerArreglo(gestor);

        Estudiante porLineal = buscador.buscarLineal(arreglo, codigo);

        int comparacionesLineal = buscador.getComparaciones();

        long tiempoLineal = buscador.getTiempoMs();

        Estudiante porBinaria = buscador.buscarBinaria(arreglo, codigo);

        int comparacionesBinaria = buscador.getComparaciones();

        long tiempoBinaria = buscador.getTiempoMs();

        StringBuilder texto = new StringBuilder();

        texto.append("BUSQUEDA LINEAL\n");
        texto.append("  Resultado     : ")
                .append(porLineal != null ? porLineal.getNombre() : "No encontrado")
                .append("\n");
        texto.append("  Comparaciones : ").append(comparacionesLineal).append("\n");
        texto.append("  Tiempo (ms)   : ").append(tiempoLineal).append("\n\n");

        texto.append("BUSQUEDA BINARIA\n");
        texto.append("  Resultado     : ")
                .append(porBinaria != null ? porBinaria.getNombre() : "No encontrado")
                .append("\n");
        texto.append("  Comparaciones : ").append(comparacionesBinaria).append("\n");
        texto.append("  Tiempo (ms)   : ").append(tiempoBinaria);

        areaResultado.setText(texto.toString());
    }
}
