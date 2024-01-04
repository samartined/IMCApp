package com.imcapp.gui;

import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.imcapp.logica.MedicionIMC;

public class HistorialFrame extends JFrame {
    // Componentes de la interfaz
    private JTable table;

    // Constructor
    public HistorialFrame(List<MedicionIMC> historial) {
        setTitle("Historial de Mediciones");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo

        // Crear la tabla
        String[] columnNames = { "Nombre", "Edad", "Peso (kg)", "Altura (en metros)", "IMC", "Estado" }; // Nombres de
                                                                                                         // las columnas
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Crear el modelo de la tabla
        table = new JTable(model); // Crear la tabla

        // Cambiar la fuente de los campos de texto
        Font font = new Font("Arial", Font.PLAIN, 16); // Crear una nueva fuente
        table.setFont(font); // Establecer la nueva fuente para la tabla

        // Cambiar el tamaño de los títulos de las columnas
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        // Cambiar el ancho de las columnas
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200); // Cambiar el ancho preferido de la columna
        }

        // Centrar el texto en las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Agregar las filas
        JScrollPane scrollPane = new JScrollPane(table); // Crear el scroll pane
        add(scrollPane); // Agregar el scroll pane a la ventana

        cargarHistorial(historial); // Cargar el historial
    }

    JTable obtenerTabla() {
        return table;
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
        table.setRowHeight(30); // Cambiar la altura de las filas
        table.repaint(); // Repintar la tabla
    }

    // Método para actualizar el historial
    public void actualizarHistorial(List<MedicionIMC> historial) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar las filas existentes

        for (MedicionIMC medicion : historial) {
            Object[] rowData = {
                    medicion.getNombre(),
                    medicion.getEdad(),
                    medicion.getPeso(),
                    medicion.getAltura(),
                    medicion.getIMCRedondeado(),
                    medicion.getEstadoIMC()
            };
            model.addRow(rowData);
        }
        table.repaint(); // Repintar la tabla
    }

}