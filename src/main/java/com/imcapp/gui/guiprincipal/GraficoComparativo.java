package com.imcapp.gui.guiprincipal;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.imcapp.logica.MedicionIMC;

public class GraficoComparativo extends JPanel {
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
                "IMC",
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
        dataset.addValue(imcUsuarioActual, "Usuario Actual", "IMC");
        return dataset;
    }

    private double obtenerIMCUsuarioActual() {
        double peso;
        double altura;

        try {
            peso = gui.getTxtPesoValue();
            altura = gui.getTxtAlturaValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }

        return peso / (altura * altura);
    }

    public void actualizarHistorial(List<MedicionIMC> historial) {
        DefaultCategoryDataset newDataset = new DefaultCategoryDataset();

        double imcUsuarioActual = obtenerIMCUsuarioActual();
        newDataset.addValue(imcUsuarioActual, "Usuario Actual", "IMC");

        for (int i = 0; i < historial.size(); i++) {
            MedicionIMC medicion = historial.get(i);
            newDataset.addValue(medicion.getIMC(), "Historial", "Registro " + (i + 1));
        }

        chart.getCategoryPlot().setDataset(newDataset);
    }
}
