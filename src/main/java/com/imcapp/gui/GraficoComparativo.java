package com.imcapp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class GraficoComparativo extends JFrame {

    public GraficoComparativo(List<MedicionIMC> historial) {
        setTitle("Gráfico Comparativo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear dataset para el gráfico
        CategoryDataset dataset = createDataset(historial);

        // Crear gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Comparación IMC",
                "Usuario",
                "IMC",
                dataset);

        // Establecer color de fondo del gráfico
        chart.setBackgroundPaint(Color.white);

        // Crear panel del gráfico y añadirlo al JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);

        // Botón para cerrar la ventana
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });

        // Añadir el botón al panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        // Añadir el panel de botones al JFrame
        add(buttonPanel);
    }

    private CategoryDataset createDataset(List<MedicionIMC> historial) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Agregar el IMC del usuario actual al dataset
        double imcUsuarioActual = obtenerIMCUsuarioActual();
        dataset.addValue(imcUsuarioActual, "Usuario Actual", "IMC");

        // Agregar el IMC de cada registro en el historial al dataset
        for (int i = 0; i < historial.size(); i++) {
            MedicionIMC medicion = historial.get(i);
            dataset.addValue(medicion.getIMC(), "Historial", "Registro " + (i + 1));
        }

        return dataset;
    }

    private double obtenerIMCUsuarioActual() {
        // Modifica esto según tu lógica para obtener el IMC del usuario actual
        // Puedes utilizar métodos de la clase Calculadora según sea necesario
        return 25.0; // Ejemplo: IMC del usuario actual es 25.0
    }

    public static void main(String[] args) {
        // Cargar el historial al inicio
        Calculadora.cargarHistorial();

        // Crear la interfaz principal
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);

            gui.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    // Al cerrar la ventana principal, crear y mostrar la ventana del gráfico
                    // comparativo
                    GraficoComparativo grafico = new GraficoComparativo(Calculadora.obtenerHistorial());
                    grafico.setLocationRelativeTo(null);
                    grafico.setVisible(true);
                }
            });
        });
    }
}
