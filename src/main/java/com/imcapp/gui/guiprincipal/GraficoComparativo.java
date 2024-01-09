package com.imcapp.gui.guiprincipal;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import com.imcapp.logica.MedicionIMC;

public class GraficoComparativo extends JPanel {
    private static final String IMC = "IMC";
    private GUI gui;
    private CategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public GraficoComparativo(GUI gui) {
        this.gui = gui;
        this.dataset = createDataset();
        this.chart = ChartFactory.createBarChart(
                "Comparación IMC",
                "Usuario",
                IMC,
                dataset);
        this.chart.setBackgroundPaint(Color.white);

        this.chartPanel = new ChartPanel(chart);
        this.chartPanel.setPreferredSize(new Dimension(800, 400));
        add(chartPanel);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double imcUsuarioActual = obtenerIMCUsuarioActual();
        dataset.addValue(imcUsuarioActual, "Usuario Actual", IMC);
        return dataset;
    }

    private double obtenerIMCUsuarioActual() {
        double peso;
        double altura;

        try {
            peso = gui.getTxtPesoValue();
            altura = gui.getTxtAlturaValue();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }

        return calcularIMC(peso, altura);
    }

    private double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }

    public double calcularMediaGlobal(List<MedicionIMC> historial) {
        return historial.stream()
                .mapToDouble(MedicionIMC::getIMC)
                .average()
                .orElse(0.0);
    }

    public void representacionDatos(List<MedicionIMC> historial) {
        DefaultCategoryDataset newDataset = new DefaultCategoryDataset();

        double imcUsuarioActual = obtenerIMCUsuarioActual();
        newDataset.addValue(imcUsuarioActual, "Tu IMC", IMC);
        double mediaGlobal = calcularMediaGlobal(historial);
        newDataset.addValue(mediaGlobal, "Media Global", IMC);

        for (int i = 0; i < historial.size(); i++) {
            MedicionIMC medicion = historial.get(i);
            newDataset.addValue(medicion.getIMC(), "Resto de Usuarios", "" + (i + 1));
        }

        chart.getCategoryPlot().setDataset(newDataset);

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, true);
        plot.setRenderer(renderer);

        renderer.setSeriesPaint(0, new Color(50, 205, 50));
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, (new Color(0, 0, 255)));
    }
}