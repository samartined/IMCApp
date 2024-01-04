package com.imcapp.gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.imcapp.logica.MedicionIMC;

public class HistorialFrame extends JFrame {
    // Componentes de la interfaz
    private JTable table;

    // Constructor
    public HistorialFrame(List<MedicionIMC> historial) {
        setTitle("Historial de Mediciones");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo

        // Crear la tabla
        String[] columnNames = { "Nombre", "Edad", "Peso", "Altura", "IMC", "Estado" }; // Nombres de las columnas
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Crear el modelo de la tabla
        table = new JTable(model); // Crear la tabla

        // Agregar las filas
        JScrollPane scrollPane = new JScrollPane(table); // Crear el scroll pane
        add(scrollPane); // Agregar el scroll pane a la ventana

        cargarHistorial(historial); // Cargar el historial
    }

    // Método para cargar el historial
    private void cargarHistorial(List<MedicionIMC> historial) {
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel(); // Obtener el modelo de la tabla

        // Agregar las filas
        for (MedicionIMC medicion : historial) {

            // Redondear IMC a un decimal

            Object[] rowData = { // Crear un arreglo con los datos de la fila

                    medicion.getNombre(),
                    medicion.getEdad(),
                    medicion.getPeso(),
                    medicion.getAltura(),
                    medicion.getIMCRedondeado(),
                    medicion.getEstadoIMC()
            };
            model.addRow(rowData); // Agregar la fila al modelo
        }
        table.repaint(); // Repintar la tabla
    }
}
